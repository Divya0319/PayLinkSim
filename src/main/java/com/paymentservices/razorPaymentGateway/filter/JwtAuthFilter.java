package com.paymentservices.razorPaymentGateway.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.paymentservices.razorPaymentGateway.config.JwtConfig;
import com.paymentservices.razorPaymentGateway.helpers.JwtHelper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	@Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private JwtConfig jwtConfig;
	
	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
	
	private List<String> excludedUrls = new ArrayList<>();


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestUri = request.getRequestURI();
		
		if(isExcluded(requestUri) || isExcludedRoot(requestUri)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String requestHeader = request.getHeader(jwtConfig.getAuthHeaderName());
		logger.info("Header : {}", requestHeader);
		String username = null;
		String token = null;
		
		if(requestHeader == null) {
            logger.info("Header is null !!");
            handleError(response, "Header is null !!");
		} 
		else {
			if(StringUtils.isBlank(requestHeader)) {
				logger.info("Header is blank !!");
				handleError(response, "Header is blank !!");
			} 
			else {
				if (requestHeader.startsWith(jwtConfig.getRequiredTokenPrefix() + " ")) {
		            // Extract token after "Bearer " prefix
//		            token = requestHeader.substring(7);
		            token = requestHeader.substring(jwtConfig.getRequiredTokenPrefix().length() + 1);
		            
		            try {
		                username = this.jwtHelper.getUsernameFromToken(token);
		            } catch (IllegalArgumentException e) {
		                logger.info("Illegal Argument while fetching the username !!");
		                handleError(response, "Illegal Argument while fetching the username !!");
		                return;
		            } catch (ExpiredJwtException e) {
		                logger.info("Given jwt token is expired !!");
		                handleError(response, "Given jwt token is expired !!");
		                return;
		            } catch (MalformedJwtException e) {
		                logger.info("Some changes have been made to the token !! Invalid Token");
		                handleError(response, "Some changes have been made to the token !! Invalid Token");
		                return;
		            } catch (Exception e) {
		                logger.info("Access Denied !! {}", e.getMessage());
		                handleError(response, "Access Denied !! " + e.getMessage());
		                return;
		            }
		        } else {
		        	logger.info("Invalid Token Scheme !!");
					handleError(response, "Invalid Token Scheme !!");
		        }
			}
		}
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Fetch user details from username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            
            if (validateToken) {
                // Set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.info("Validation failed !!");
                handleError(response, "Validation failed !!");
                return;
            }
        }
		
        if(!response.isCommitted()) {
        	filterChain.doFilter(request, response); 
        }
		
	}
	
	private boolean isExcluded(String requestUri) {
		return excludedUrls.stream().anyMatch(excludedUrl -> requestUri.startsWith(excludedUrl));
	}
	
	private boolean isExcludedRoot(String requestUri) {
		if(requestUri.equals("/")) {
			return jwtConfig.isRootExcluded();
		} else {
			return false;
		}
	}
	
	private void handleError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write("{\"error\": \"" + message + "\"}");
        writer.flush();
        writer.close();
    }

	public List<String> getExcludedUrls() {
		return excludedUrls;
	}

	public void addExcludedUrl(String url) {
		this.excludedUrls.add(url);
	}

}

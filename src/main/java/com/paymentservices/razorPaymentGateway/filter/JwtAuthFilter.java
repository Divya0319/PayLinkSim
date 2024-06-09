package com.paymentservices.razorPaymentGateway.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
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
	
	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
	
	private static final List<String> EXCLUDED_URLS = Arrays.asList(
			"/platformpayment/swagger-ui/",  // Exclude Swagger UI 
            "/v2/api-docs/",
            "/v3/api-docs/",
            "/swagger-resources/",
            "/webjars/",
            "/swagger-ui/"
    );

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestUri = request.getRequestURI();
		if(isExcluded(requestUri)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String requestHeader = request.getHeader("Authorization");
		logger.info("Header : {}", requestHeader);
		String username = null;
		String token = null;
		 
	    try {
	    	if(requestHeader == null) {
	    		 
	            logger.info("Header is null!! ");
	            handleError(response, "Header is null!! ");
		        
	    	} else if(requestHeader.trim().isEmpty()) {
	    		logger.info("Header is blank !!");
	            handleError(response, "Header is blank !!");
	    	}
	    	else if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
	    		 
		            // looking good
		            token = requestHeader.substring(7);
	
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
		                logger.info("Some changes has done in token !! Invalid Token");
		                handleError(response, "Some changes has done in token !! Invalid Token");
		                return;
		            } catch (Exception e) {
		                logger.info("Access Denied !! {}", e.getMessage());
		                handleError(response, "Access Denied !! " + e.getMessage());
		                return;
		            }
	    	} else {
	    		logger.info("Invalid Header value !!");
	            handleError(response, "Invalid Header value !!");
	    	}
	    	

	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            // fetch user detail from username
	            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
	            Boolean isValidToken = this.jwtHelper.validateToken(token, userDetails);

	            if (isValidToken) {
	                // set the authentication
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
	        else {
	        	// If response is already committed, log a message or handle the situation accordingly
	            logger.warn("Response is already committed. Unable to proceed with filter chain.");
	            // Handle the situation as needed, such as throwing an exception or returning an error response.
	        }
	        
	    } catch (Exception e) {
	        logger.error("An error occurred while processing the request", e);
	        handleError(response, "An internal error occurred while processing the request.");
	    }
		
	}
	
	private boolean isExcluded(String requestUri) {
		return EXCLUDED_URLS.stream().anyMatch(excludedUrl -> requestUri.startsWith(excludedUrl));
	}
	
	private void handleError(HttpServletResponse response, String message) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.write("{\"error\": \"" + message + "\"}");
		writer.flush();
		writer.close();
	}

}

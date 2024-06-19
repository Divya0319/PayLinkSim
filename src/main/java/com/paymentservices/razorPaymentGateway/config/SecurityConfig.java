package com.paymentservices.razorPaymentGateway.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.paymentservices.razorPaymentGateway.filter.JwtAuthFilter;
import com.paymentservices.razorPaymentGateway.helpers.JwtAuthenticationEntryPoint;
import com.paymentservices.razorPaymentGateway.helpers.UrlPatternConverter;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtAuthenticationEntryPoint authEntryPoint;
	
	@Autowired
	private JwtAuthFilter authFilter;
	
	@Autowired
	private JwtConfig jwtConfig;
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> {
                jwtConfig.getExcludedUrls().forEach(url -> authorize.requestMatchers(url).permitAll());
                jwtConfig.getAuthenticatedUrls().forEach(url -> authorize.requestMatchers(url).authenticated());
                authorize.anyRequest().authenticated();
            })
            .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        List<String> convertedUrls = new ArrayList<>();
        convertedUrls.addAll(new UrlPatternConverter().processWildCardUrls(convertedUrls));
        
        for(String url : convertedUrls) {
        	authFilter.addExcludedUrl(url);
        }

        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
	

}

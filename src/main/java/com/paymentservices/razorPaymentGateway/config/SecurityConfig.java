package com.paymentservices.razorPaymentGateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.paymentservices.razorPaymentGateway.filter.JwtAuthFilter;
import com.paymentservices.razorPaymentGateway.helpers.JwtAuthenticationEntryPoint;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtAuthenticationEntryPoint authEntryPoint;
	
	@Autowired
	private JwtAuthFilter authFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(authorize -> 
				authorize.requestMatchers("/auth/login").permitAll()
                .requestMatchers("/payment/paymentCallback").permitAll() // Exclude payment callback endpoint
				.requestMatchers("/platformpayment/swagger-ui/*",  // Exclude Swagger UI 
                        "/v3/api-docs/*",
                        "/swagger-resources/**",
                        "/swagger-ui/*",
                        "/webjars/**").permitAll() 
				.requestMatchers("/payment/**").authenticated()
				.anyRequest().authenticated())
		.exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	

}

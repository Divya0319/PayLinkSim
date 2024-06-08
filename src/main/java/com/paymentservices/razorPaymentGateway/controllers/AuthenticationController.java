package com.paymentservices.razorPaymentGateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymentservices.razorPaymentGateway.services.MyUserDetailsService;
import com.paymentservices.razorPaymentGateway.utils.JwtUtil;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@PostMapping("/authenticate")
	public String createAuthenticationToken(@RequestParam("username") String username, 
			@RequestParam("password") String password) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username, password));
		} catch(Exception e) {
			throw new Exception("Incorrect username or password", e);
		}
		
		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
		final String jwt = jwtUtil.generateToken(userDetails.getUsername());
		
		return jwt;
	}

}

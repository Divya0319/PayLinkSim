package com.paymentservices.razorPaymentGateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymentservices.razorPaymentGateway.helpers.JwtHelper;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private JwtHelper helper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Operation(summary = "Generates a unique JWT auth token, valid for 1 min")
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam("username") String username, @RequestParam("password") String password) {
		this.doAuthenticate(username, password);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		String token = this.helper.generateToken(userDetails);
		
		return new ResponseEntity<>(token, HttpStatus.OK);
		
	}
	
	private void doAuthenticate(String username, String password) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
		try {
			authManager.authenticate(authentication);
		} catch(BadCredentialsException e) {
			throw new BadCredentialsException("Invalid username or password");
		}
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public String exceptionHandler() {
		return "Credentials Invalid!!";
	}

}

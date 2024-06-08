package com.paymentservices.razorPaymentGateway.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private String SECRET_KEY = "6E7D1A929D61C3BEB878ABE9A7DEE";
	
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}
	
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	private Key getSignKey() {
		byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyByte);
	}

	
}

package com.paymentservices.razorPaymentGateway.helpers;

import java.util.List;
import java.util.stream.Collectors;

public class UrlPatternConverter {
	
	public List<String> processWildCardUrls(List<String> wildCardUrls) {
		return wildCardUrls.stream()
				.map(this::convertGlobToRegex)
				.collect(Collectors.toList());
	}
	
	private String convertGlobToRegex(String globPattern) {
		String regexPattern = globPattern
				.replace("/**", "/")
				.replace("/*", "/");
		
		return regexPattern;
	}

}

package com.paymentservices.razorPaymentGateway.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class JwtConfig {
	private String secretKey = "asasfasfasfasfASAFDADFASDASFADFADFSDFADFAFDFSDFSDFSDFS";   // default secret key
	private int jwtTokenValidityInMin = 60; // default to 60 min
	private List<String> excludedUrls = new ArrayList<>();
	private List<String> authenticatedUrls = new ArrayList<>();
	private String authHeaderName = "Authorization";  // default header name
	private String requiredTokenPrefix = "Bearer ";  // default prefix
	private boolean rootExcluded = false;
	
	public String getSecretKey() {
        return secretKey;
    }
	
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	public int getJwtTokenValidityInMin() {
        return jwtTokenValidityInMin;
    }

    public void setJwtTokenValidityInMin(int jwtTokenValidityInMin) {
        this.jwtTokenValidityInMin = jwtTokenValidityInMin;
    }

    public List<String> getExcludedUrls() {
        return excludedUrls;
    }

    public void addExcludedUrl(String url) {
        this.excludedUrls.add(url);
    }

    public List<String> getAuthenticatedUrls() {
        return authenticatedUrls;
    }

    public void addAuthenticatedUrl(String url) {
        this.authenticatedUrls.add(url);
    }
    
    public String getAuthHeaderName() {
        return authHeaderName;
    }

    public void setAuthHeaderName(String authHeaderName) {
        this.authHeaderName = authHeaderName;
    }

	public boolean isRootExcluded() {
		return rootExcluded;
	}

	public void setRootExcluded(boolean rootExcluded) {
		this.rootExcluded = rootExcluded;
	}

	public String getRequiredTokenPrefix() {
		return requiredTokenPrefix;
	}

	public void setRequiredTokenPrefix(String requiredTokenPrefix) {
		this.requiredTokenPrefix = requiredTokenPrefix;
	}
    
}

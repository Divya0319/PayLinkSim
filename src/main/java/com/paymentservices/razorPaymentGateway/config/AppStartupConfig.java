package com.paymentservices.razorPaymentGateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppStartupConfig {
	
	@Autowired
    private JwtConfig jwtConfig;
	
	@Value("${jwt.token.validity}")
	private int jwtTokenValidityInMin;

    @Bean
    public JwtConfig configureJwtParameters() {
        // Set the secret key (should be kept as long as possible, it makes the generated token string longer)
        jwtConfig.setSecretKey("adaafaasfdaadfAPKFSKPHJUGHJUYGGassdsffffKJJHGHGGJJHHGFSASDDSDA");

        // Set token validity in minutes
//        jwtConfig.setJwtTokenValidityInMin(10 * 60);
        jwtConfig.setJwtTokenValidityInMin(jwtTokenValidityInMin);

        // Set the custom authorization header name
        jwtConfig.setAuthHeaderName("Authorization");
        
        // Set the custom token prefix
        jwtConfig.setRequiredTokenPrefix("JWT");

        // Add excluded URLs
        jwtConfig.addExcludedUrl("/");
        jwtConfig.addExcludedUrl("/auth/login");
        jwtConfig.addExcludedUrl("/payment/paymentCallback");
        jwtConfig.addExcludedUrl("/platformpayment/swagger-ui/*");
        jwtConfig.addExcludedUrl("/v3/api-docs/*");
        jwtConfig.addExcludedUrl("/swagger-resources/**");
        jwtConfig.addExcludedUrl("/swagger-ui/*");
        jwtConfig.addExcludedUrl("/webjars/**");
        jwtConfig.addExcludedUrl("/favicon.ico");
        jwtConfig.setRootExcluded(true);

        // Add authenticated URLs
        jwtConfig.addAuthenticatedUrl("/payment/**");
        
        return jwtConfig;
    }
}

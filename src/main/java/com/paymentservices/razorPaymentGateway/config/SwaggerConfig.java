package com.paymentservices.razorPaymentGateway.config;

import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
    	return GroupedOpenApi.builder()
    			.group("razorPay-gateway")
    			.packagesToScan("com.paymentservices.razorPaymentGateway.controllers")
    			.build();
    }

    @Bean
    public OpenAPI orderProcessOpenAPI() {
    	return new OpenAPI()
    			.addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
    			.components(new Components()
    					.addSecuritySchemes("bearerAuth", createAPIKeyScheme())   // for JWT Token 
//    					.addSecuritySchemes("RazorPay Key", createRazorPayKeyScheme())  // for Razorpay key
//    					.addSecuritySchemes("RazorPay secret", createRazorPaySecretScheme())  // for Razorpay secret
    					)
    			.security(List.of(new SecurityRequirement().addList("bearerAuth")))
    			.info(new Info().title("RazorPay Payment Gateway")
				.description("For API Learning")
				.version("1.0.0")
				.license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.externalDocs(new ExternalDocumentation()
				.description("SpringShop Wiki Documentation")
	            .url("https://springshop.wiki.github.org/docs"));
    }
    
    private SecurityScheme createAPIKeyScheme() {
    	return new SecurityScheme().type(SecurityScheme.Type.HTTP)
    			.name("Authorization")
    			.description("JWT auth description")
    			.bearerFormat("JWT")
    			.in(SecurityScheme.In.HEADER)
    			.scheme("bearer")
    			;
    }
    
    private SecurityScheme createRazorPayKeyScheme() {
    	return new SecurityScheme().type(SecurityScheme.Type.APIKEY)
    			.in(SecurityScheme.In.HEADER)
                .name("x-razorpay-key")
                .description("Razorpay Key");
    }
    
    private SecurityScheme createRazorPaySecretScheme() {
    	return new SecurityScheme().type(SecurityScheme.Type.APIKEY)
    			.in(SecurityScheme.In.HEADER)
                .name("x-razorpay-secret")
                .description("Razorpay Secret");
    }

}

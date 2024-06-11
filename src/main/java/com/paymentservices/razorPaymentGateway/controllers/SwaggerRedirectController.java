package com.paymentservices.razorPaymentGateway.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SwaggerRedirectController {

	@GetMapping("/platformpayment/swagger-ui/")
	public RedirectView redirectToSwaggerUI() {
		return new RedirectView("/platformpayment/swagger-ui/index.html#");
	}
	
	@GetMapping("/")
	public RedirectView redirectToSwaggerUIFromRoot() {
		return new RedirectView("/platformpayment/swagger-ui/index.html#");
	}
	
}

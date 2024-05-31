package com.paymentservices.razorPaymentGateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymentservices.razorPaymentGateway.models.PaymentStatus;
import com.paymentservices.razorPaymentGateway.services.PaymentService;

@RestController
public class PaymentController {
	
	private PaymentService paymentService;
	
	@Autowired
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@PostMapping("/payment/createLink")
	public String createPaymentLink(@RequestParam String orderId) {
		return paymentService.createLink(orderId);
	}
	
	@GetMapping("/payment/getPaymentStatus")
	public PaymentStatus getPaymentStatus(@RequestParam("paymentId") String paymentId, @RequestParam("orderId") String orderId) {
		return paymentService.getPaymentStatus(paymentId, orderId);
	}

}

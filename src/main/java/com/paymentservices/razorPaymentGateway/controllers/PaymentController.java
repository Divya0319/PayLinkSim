package com.paymentservices.razorPaymentGateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymentservices.razorPaymentGateway.models.PaymentStatus;
import com.paymentservices.razorPaymentGateway.models.PaymentSuccess;
import com.paymentservices.razorPaymentGateway.services.PaymentService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class PaymentController {
	
	private PaymentService paymentService;
	
	@Autowired
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@Operation(summary = "It creates the payment link, which we can copy paste in browser.")
	@PostMapping("/payment/createLink")
	public String createPaymentLink(@RequestParam String orderId, @RequestParam int amount) {
		return paymentService.createLink(orderId, amount);
	}
	
	@Operation(summary = "It returns whether a payment was success or failure")
	@GetMapping("/payment/getPaymentStatus")
	public PaymentStatus getPaymentStatus(@RequestParam("paymentId") String paymentId, @RequestParam("orderId") String orderId) {
		return paymentService.getPaymentStatus(paymentId, orderId);
	}
	
	@Hidden
	@Operation(summary = "Used for internal callbacks, not to be used by user")
	@GetMapping("/payment/paymentCallback")
    public PaymentSuccess handleRazorpayCallback(
            @RequestParam("razorpay_payment_id") String paymentId,
            @RequestParam("razorpay_payment_link_id") String paymentLinkId,
            @RequestParam("razorpay_payment_link_reference_id") String paymentLinkReferenceId,
            @RequestParam("razorpay_payment_link_status") String paymentLinkStatus,
            @RequestParam("razorpay_signature") String signature) {
        
        // Process the callback parameters
        return paymentService.processPaymentSuccess(paymentId, paymentLinkId, paymentLinkReferenceId, paymentLinkStatus, signature);
 
	}
}

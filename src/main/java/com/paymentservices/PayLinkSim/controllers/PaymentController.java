package com.paymentservices.PayLinkSim.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymentservices.PayLinkSim.models.PaymentStatus;
import com.paymentservices.PayLinkSim.models.PaymentSuccess;
import com.paymentservices.PayLinkSim.services.PaymentService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class PaymentController {

	private PaymentService paymentService;

	@Autowired
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@Operation(summary = "Creates the payment link, which we can copy-paste in browser.")
	@PostMapping("/payment/createLink")
	public ResponseEntity<String> createPaymentLink(@RequestParam String orderId, @RequestParam int amount) {
		try {
			String paymentLink = paymentService.createLink(orderId, amount);
			return ResponseEntity.ok(paymentLink);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@Operation(summary = "Returns whether a payment was success or failure, based on its id")
	@GetMapping("/payment/getPaymentStatus")
	public PaymentStatus getPaymentStatus(@RequestParam("paymentId") String paymentId,
			@RequestParam("orderId") String orderId) {
		return paymentService.getPaymentStatus(paymentId, orderId);
	}

	@Hidden
	@Operation(summary = "Used for internal callbacks, not to be used by user")
	@GetMapping("/payment/paymentCallback")
	public PaymentSuccess handleRazorpayCallback(@RequestParam("razorpay_payment_id") String paymentId,
			@RequestParam("razorpay_payment_link_id") String paymentLinkId,
			@RequestParam("razorpay_payment_link_reference_id") String paymentLinkReferenceId,
			@RequestParam("razorpay_payment_link_status") String paymentLinkStatus,
			@RequestParam("razorpay_signature") String signature) {

		// Process the callback parameters
		return paymentService.processPaymentSuccess(paymentId, paymentLinkId, paymentLinkReferenceId, paymentLinkStatus,
				signature);

	}
}

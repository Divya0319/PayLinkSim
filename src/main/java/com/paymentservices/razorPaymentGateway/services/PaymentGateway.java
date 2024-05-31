package com.paymentservices.razorPaymentGateway.services;

import org.springframework.stereotype.Component;

import com.paymentservices.razorPaymentGateway.dto.PaymentLinkRequestDto;
import com.paymentservices.razorPaymentGateway.models.PaymentStatus;

@Component
public interface PaymentGateway {
	String createPaymentLink(PaymentLinkRequestDto paymentLinkRequestDto);
    PaymentStatus getStatus(String paymentId, String orderId);
}

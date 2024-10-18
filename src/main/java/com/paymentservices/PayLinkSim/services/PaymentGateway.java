package com.paymentservices.PayLinkSim.services;

import org.springframework.stereotype.Component;

import com.paymentservices.PayLinkSim.dto.PaymentLinkRequestDto;
import com.paymentservices.PayLinkSim.models.PaymentStatus;

@Component
public interface PaymentGateway {
	String createPaymentLink(PaymentLinkRequestDto paymentLinkRequestDto);
    PaymentStatus getStatus(String paymentId, String orderId);
}

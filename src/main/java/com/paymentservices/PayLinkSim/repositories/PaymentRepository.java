package com.paymentservices.PayLinkSim.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentservices.PayLinkSim.models.PaymentDetails;

public interface PaymentRepository extends JpaRepository<PaymentDetails, Long> {
	Optional<PaymentDetails> findByOrderId(String orderId);
}

package com.paymentservices.PayLinkSim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentservices.PayLinkSim.models.PaymentSuccess;

public interface PaymentSuccessRepository extends JpaRepository<PaymentSuccess, String> {
}
package com.paymentservices.razorPaymentGateway.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentservices.razorPaymentGateway.models.PaymentSuccess;

public interface PaymentSuccessRepository extends JpaRepository<PaymentSuccess, String> {
}
package com.paymentservices.PayLinkSim.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymentservices.PayLinkSim.dto.PaymentLinkRequestDto;
import com.paymentservices.PayLinkSim.models.PaymentDetails;
import com.paymentservices.PayLinkSim.models.PaymentStatus;
import com.paymentservices.PayLinkSim.models.PaymentSuccess;
import com.paymentservices.PayLinkSim.repositories.PaymentRepository;
import com.paymentservices.PayLinkSim.repositories.PaymentSuccessRepository;

@Service
public class PaymentService {
	
	private PaymentGateway paymentGateway;
	
	private PaymentRepository paymentRepository;
	
	private PaymentSuccessRepository paymentSuccessRepository;
	
	@Autowired
	public PaymentService(PaymentGateway paymentGateway, PaymentRepository paymentRepository, PaymentSuccessRepository paymentSuccessRepository) {
		this.paymentGateway = paymentGateway;
		this.paymentRepository = paymentRepository;
		this.paymentSuccessRepository = paymentSuccessRepository;
	}


	public String createLink(String orderId, int amount) {
		/*
        Make a call to order service and get the order details.
        OrderDetail order = restTemplate.getMapping(orderId)
        name = order.getCustomerName()
        amount = order.getAmount()
        phone = order.getCustomerPhone()
       */
        PaymentLinkRequestDto paymentLinkRequestDto = new PaymentLinkRequestDto();
        paymentLinkRequestDto.setCustomerName("Divya");
        paymentLinkRequestDto.setOrderId(orderId);
        paymentLinkRequestDto.setPhone("7000493027");
        paymentLinkRequestDto.setAmount(amount * 100);

        // Generate payment link using the payment gateway
        String paymentLink = paymentGateway.createPaymentLink(paymentLinkRequestDto);
        
        if (paymentLink.contains("Failed to create payment link")) {
            throw new RuntimeException(paymentLink);
        }

        // Save payment details in the repository
        PaymentDetails paymentResponse = new PaymentDetails();
        paymentResponse.setPaymentLink(paymentLink);
        paymentResponse.setOrderId(orderId);
        paymentRepository.save(paymentResponse);

        return paymentLink;
	}
	
	public PaymentStatus getPaymentStatus(String paymentId, String orderId) {
        // Retrieve payment details by order ID
        Optional<PaymentDetails> paymentDetails = paymentRepository.findByOrderId(orderId);

        if(paymentDetails.isEmpty()){
            throw new RuntimeException("Payment not found");
        }

        // Get payment status from the payment gateway
        PaymentStatus status = paymentGateway.getStatus(paymentId, orderId);

        // Update and save payment details with the new status
        PaymentDetails paymentResponse = paymentDetails.get();
        paymentResponse.setStatus(status);
        paymentRepository.save(paymentResponse);

        return status;
    }
	
	public PaymentSuccess processPaymentSuccess(String paymentId,String paymentLinkId, String paymentLinkReferenceId, 
			String paymentLinkStatus, String signature) {
		// Create PaymentSuccess entity
        PaymentSuccess paymentSuccess = new PaymentSuccess();
        paymentSuccess.setPaymentId(paymentId);
        paymentSuccess.setPaymentLinkId(paymentLinkId);
        paymentSuccess.setPaymentLinkReferenceId(paymentLinkReferenceId);
        paymentSuccess.setPaymentLinkStatus(paymentLinkStatus);
        paymentSuccess.setSignature(signature);
        
     // Save to the database
        PaymentSuccess savedPaymentSuccess = paymentSuccessRepository.save(paymentSuccess);
        
     // Retrieve the corresponding PaymentDetails entity
        Optional<PaymentDetails> paymentDetailsOptional = paymentRepository.findByOrderId(paymentLinkReferenceId);
        
        if(paymentDetailsOptional.isPresent()) {
        	PaymentDetails paymentDetails = paymentDetailsOptional.get();
        	paymentDetails.setPaymentSuccess(savedPaymentSuccess); // Set the bidirectional relationship
            paymentRepository.save(paymentDetails);  // Update PaymentDetails to reference PaymentSuccess
            
            savedPaymentSuccess.setPaymentDetails(paymentDetails);
            savedPaymentSuccess.getPaymentDetails().setPaymentSuccess(null);
        } else {
        	throw new RuntimeException("PaymentDetails not found for the given orderId");
        }
        
        
        return savedPaymentSuccess;
	}

}

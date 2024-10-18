package com.paymentservices.PayLinkSim.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class PaymentSuccess {
    
    @Id
    private String paymentId;
    private String paymentLinkId;
    private String paymentLinkReferenceId;
    private String paymentLinkStatus;
    private String signature;
    
    @OneToOne(mappedBy = "paymentSuccess")
    private PaymentDetails paymentDetails;

   
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentLinkId() {
        return paymentLinkId;
    }

    public void setPaymentLinkId(String paymentLinkId) {
        this.paymentLinkId = paymentLinkId;
    }

    public String getPaymentLinkReferenceId() {
        return paymentLinkReferenceId;
    }

    public void setPaymentLinkReferenceId(String paymentLinkReferenceId) {
        this.paymentLinkReferenceId = paymentLinkReferenceId;
    }

    public String getPaymentLinkStatus() {
        return paymentLinkStatus;
    }

    public void setPaymentLinkStatus(String paymentLinkStatus) {
        this.paymentLinkStatus = paymentLinkStatus;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
    
    public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	
}

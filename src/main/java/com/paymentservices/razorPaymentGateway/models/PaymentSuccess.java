package com.paymentservices.razorPaymentGateway.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PaymentSuccess {
    
    @Id
    private String paymentId;
    private String paymentLinkId;
    private String paymentLinkReferenceId;
    private String paymentLinkStatus;
    private String signature;

   
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
}

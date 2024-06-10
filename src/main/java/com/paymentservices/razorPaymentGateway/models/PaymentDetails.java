package com.paymentservices.razorPaymentGateway.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class PaymentDetails extends BaseModel {

	private String orderId;
    private String paymentId;
    private String PaymentLink;
    
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    
    @OneToOne
    @JoinColumn(name = "paymentId", referencedColumnName = "paymentId")
    private PaymentSuccess paymentSuccess;
    
	public String getOrderId() {
		return orderId;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getPaymentId() {
		return paymentId;
	}
	
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	public String getPaymentLink() {
		return PaymentLink;
	}
	
	public void setPaymentLink(String paymentLink) {
		PaymentLink = paymentLink;
	}
	
	public PaymentStatus getStatus() {
		return status;
	}
	
	public void setStatus(PaymentStatus status) {
		this.status = status;
	}
	
	public PaymentSuccess getPaymentSuccess() {
		return paymentSuccess;
	}
	
	public void setPaymentSuccess(PaymentSuccess paymentSuccess) {
		this.paymentSuccess = paymentSuccess;
	}
    
}

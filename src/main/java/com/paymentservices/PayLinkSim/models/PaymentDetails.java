package com.paymentservices.PayLinkSim.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class PaymentDetails extends BaseModel {

	private String orderId;
    private String paymentLink;
    
    @JsonInclude(Include.NON_NULL)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    
    @JsonInclude(Include.NON_NULL)
    @OneToOne
    @JoinColumn(name = "paymentId", referencedColumnName = "paymentId")
    private PaymentSuccess paymentSuccess;
    
	public String getOrderId() {
		return orderId;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getPaymentLink() {
		return paymentLink;
	}
	
	public void setPaymentLink(String paymentLink) {
		this.paymentLink = paymentLink;
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

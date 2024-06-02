package com.paymentservices.razorPaymentGateway.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class PaymentDetails extends BaseModel {

	private String orderId;
    private String paymentLink;
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

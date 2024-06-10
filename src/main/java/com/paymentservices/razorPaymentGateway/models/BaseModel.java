package com.paymentservices.razorPaymentGateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseModel {
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}

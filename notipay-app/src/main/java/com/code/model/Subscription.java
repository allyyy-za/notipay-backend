package com.code.model;

import java.time.LocalDate;
//import java.util.Date;
//import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
public class Subscription {
	
	private int subscriptionId;
	private String subscriptionName;
	private float amount;
	private LocalDate renewalDate;
	private int userId;
	
	public String getSubscriptionName() {
		return subscriptionName;
	}
	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public LocalDate getRenewalDate() {
		return renewalDate;
	}
	public void setRenewalDate(LocalDate renewalDate) {
		this.renewalDate = renewalDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(int subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

}

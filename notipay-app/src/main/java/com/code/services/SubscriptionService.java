package com.code.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.code.dao.SubscriptionDao;
import com.code.model.Subscription;

@Service
public class SubscriptionService {
	
	private final SubscriptionDao subscriptionDao;
	
	public SubscriptionService(SubscriptionDao subscriptionDao) {
		this.subscriptionDao = subscriptionDao;
	}

	public ResponseEntity<?> addSubscription(int userId, Subscription request) {
		Subscription subscription = new Subscription();
		subscription.setSubscriptionName(request.getSubscriptionName());
		subscription.setAmount(request.getAmount());
		subscription.setRenewalDate(request.getRenewalDate());
			
		int result = subscriptionDao.save(userId, request);
		
		if(result > 0) {
			return ResponseEntity.ok().body("You have successfully added a subscription.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is an error in adding the subscription.");
		} 
	}
	
	public ResponseEntity<?> getSubscriptions(int userId) {
		List<Subscription> listOfSubscriptions = subscriptionDao.findAllSubscription(userId);
		
		if(listOfSubscriptions == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is an error in getting the subscriptions.");
		}
		
		return ResponseEntity.ok().body(listOfSubscriptions);
	}
	
	public ResponseEntity<?> deleteSubscription(int userId, int subscriptionId) {
		int result = subscriptionDao.deleteById(userId, subscriptionId);
		
		if(result > 0 ) {
			return ResponseEntity.ok().body("You have successfully deleted the subscription.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is an error in deleting the subscription.");
		} 
	}
	
	public ResponseEntity<?> editSubscription(int userId, Subscription subscription, int subscriptionId) {
		int result = subscriptionDao.update(userId, subscription, subscriptionId);
		
		if(result > 0 ) {
			return ResponseEntity.ok().body("You have successfully edited the subscription.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is an error in editing the subscription.");
		} 
	}
}

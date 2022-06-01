package com.code.dao;

import java.util.List;
//import java.util.List;
import com.code.model.Subscription;
//import com.code.model.User;

public interface SubscriptionDao {
	public List<Subscription> findAllSubscription(int userId);
	public Subscription findById(int userId, int subscriptionId);
	public int deleteById(int userId, int subscriptionId);
	public int save(int userId, Subscription subscription);
	public int update(int userId, Subscription subscription, int id);
}

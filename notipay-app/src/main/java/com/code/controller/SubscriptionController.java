package com.code.controller;

import java.time.LocalDate;
//import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
import com.code.dao.SubscriptionDao;
import com.code.model.Subscription;

@Controller
public class SubscriptionController {

	@Autowired
	private Subscription subscription;
	
	@Autowired
	private SubscriptionDao subscriptionDao;
	
	@RequestMapping("/subscriptions/{userId}")
	public String subscriptionPage(Model model, @PathVariable int userId) {
		model.addAttribute("userId", userId);
		return "subscription";
	} 
	
	@PostMapping("/add-subscription/{userId}")
	private String addSubscription(Model model,
			@RequestParam("subscriptionName") String subscriptionName,
			@RequestParam("amount") float amount,
			@RequestParam("renewalDate") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate renewalDate,
			@PathVariable int userId) {

		subscription = new Subscription();
		subscription.setSubscriptionName(subscriptionName);
		subscription.setAmount(amount);
		subscription.setRenewalDate(renewalDate);


		
		if(subscription==null) {
			model.addAttribute("error", "Empty fields.");
			return "subscription";
		} else {
			subscriptionDao.save(userId, subscription);
		}
		
		return "index";
	}
}

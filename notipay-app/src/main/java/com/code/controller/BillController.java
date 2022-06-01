package com.code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.dao.UserDao;
import com.code.model.Bill;
import com.code.model.User;
import com.code.services.AccountDetails;
import com.code.services.BillService;

@RestController
@RequestMapping("/api/bills")
@CrossOrigin
public class BillController {
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private UserDao userDao;
	
	@GetMapping("")
	public ResponseEntity<?> getBills(@AuthenticationPrincipal AccountDetails account) {
		User user = userDao.getUserByUsername(account.getUsername());
		return billService.getBills(user.getUserId());
	}
	
	@PostMapping("")
	public ResponseEntity<?> addBill(@AuthenticationPrincipal AccountDetails account, @RequestBody Bill request) {
		User user = userDao.getUserByUsername(account.getUsername());
		return billService.addBill(user.getUserId(), request);
	}
	
	@DeleteMapping("/billId={billId}")
	public ResponseEntity<?> deleteBill(@AuthenticationPrincipal AccountDetails account, @PathVariable String billId) {
		User user = userDao.getUserByUsername(account.getUsername());
		return billService.deleteBill(user.getUserId(), Integer.parseInt(billId));
	}
	
	@PutMapping("/billId={billId}")
	public ResponseEntity<?> editBill(@AuthenticationPrincipal AccountDetails account, @RequestBody Bill bill, @PathVariable String billId) {
		User user = userDao.getUserByUsername(account.getUsername());
		System.out.println(bill.getDueDate());
		return billService.editBill(user.getUserId(), bill, Integer.parseInt(billId));
	}
	
	@GetMapping("/billId={billId}")
	public ResponseEntity<?> viewBill(@AuthenticationPrincipal AccountDetails account, @PathVariable String billId) {
		User user = userDao.getUserByUsername(account.getUsername());
		return billService.viewBill(user.getUserId(), Integer.parseInt(billId));
	}
}

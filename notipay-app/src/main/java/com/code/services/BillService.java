package com.code.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.code.dao.BillDao;
import com.code.model.Bill;


@Service
public class BillService {
	
	private final BillDao billDao;
	
	public BillService(BillDao billDao) {
		this.billDao = billDao;
	}
	
	public ResponseEntity<?> getBills(int userId) {
		List<Bill> listOfSubscriptions = billDao.findAllBills(userId);
		
		if(listOfSubscriptions == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is an error in getting the bills.");
		}
		
		return ResponseEntity.ok().body(listOfSubscriptions);
	}
	
	public ResponseEntity<?> addBill(int userId, Bill request) {
		Bill bill = new Bill();
		bill.setBillName(request.getBillName());
		bill.setAmount(request.getAmount());
		bill.setDueDate(request.getDueDate());
		
		int result = billDao.save(userId, bill);
		
		if(result > 0) {
			return ResponseEntity.ok().body("You have successfully added a bill.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is an error in adding a bill.");
		}
	}
	
	public ResponseEntity<?> deleteBill(int userId, int subscriptionId) {
		int result = billDao.delete(userId, subscriptionId);
		
		if(result > 0) {
			return ResponseEntity.ok().body("You have successfully deleted a bill.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is an error in deleting a bill.");
		}
	}

	public ResponseEntity<?> editBill(int userId, Bill bill, int billId) {
		int result = billDao.update(userId, bill, billId);
		
		if(result > 0) {
			return ResponseEntity.ok().body("You have successfully edited a bill.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is an error in editing a bill.");
		}
	}
	
	public ResponseEntity<?> viewBill(int userId, int billId) {
		Bill bill = billDao.findById(userId, billId);
		
		if(bill != null) {
			return ResponseEntity.ok().body(bill);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is an error in viewing a bill.");
		}
	}


}

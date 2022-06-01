package com.code.dao;

import java.util.List;
import com.code.model.Bill;

public interface BillDao {
	public List<Bill> findAllBills(int userId);
	public Bill findById(int userId, int billId);
	public int delete(int userId, int billId);
	public int save(int userId, Bill bill);
	public int update(int userId, Bill bill, int billId);
}

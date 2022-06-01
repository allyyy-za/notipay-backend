package com.code.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.code.model.Bill;

@Repository
public class BillDaoImpl implements BillDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Bill> findAllBills(int userId) {
		String sql = "SELECT * from bill WHERE userId='" + userId + "'";
		List<Bill> listOfBills = jdbcTemplate.query(sql, new RowMapper<Bill>() {
			@Override
			public Bill mapRow(ResultSet rs, int rowNum) throws SQLException {
				Bill bill = new Bill();
				bill.setBillId(rs.getInt("billId"));			
				bill.setBillName(rs.getString("billName"));
				bill.setAmount(rs.getFloat("amount"));
				bill.setDueDate(rs.getDate("dueDate").toLocalDate());
				return bill;
			}
		});
		
		return listOfBills;
	}

	@Override
	public int delete(int userId, int billId) {
		String sql = "DELETE FROM bill WHERE userId='" + userId + "' AND billId='" + billId + "'";
		return jdbcTemplate.update(sql);
	}

	@Override
	public int save(int userId, Bill bill) {
		Date sqlDate = Date.valueOf(bill.getDueDate());
		String sql = "INSERT INTO bill(billName, amount, dueDate, userId) VALUES('"
				+ bill.getBillName() + "','"
				+ bill.getAmount() + "','"
				+ sqlDate + "','"
				+ userId + "')";
		return jdbcTemplate.update(sql);
	}

	@Override
	public int update(int userId, Bill bill, int billId) {
		Date sqlDate = Date.valueOf(bill.getDueDate());
		String sql = "UPDATE bill SET billName='" + bill.getBillName() + "', amount='"
				+ bill.getAmount() + "', dueDate='" + sqlDate  + "' WHERE userId='" 
				+ userId + "' AND billId='" + billId + "'";
		return jdbcTemplate.update(sql);
	}

	@Override
	public Bill findById(int userId, int billId) {
		String sql = "SELECT * from bill WHERE userId='" + userId + "' and billId='" + billId + "'";
		return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Bill.class));
	}

}

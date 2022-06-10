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
import com.code.model.Subscription;

@Repository
public class SubscriptionDaoImpl implements SubscriptionDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Subscription> findAllSubscription(int userId) {
		String sql = "SELECT * from subscription WHERE userId='" + userId + "'";
		List<Subscription> listOfSubscriptions = jdbcTemplate.query(sql, new RowMapper<Subscription>() {
			@Override
			public Subscription mapRow(ResultSet rs, int rowNum) throws SQLException {
				Subscription subscription = new Subscription();
				subscription.setSubscriptionId(rs.getInt("subscriptionId"));
				subscription.setSubscriptionName(rs.getString("subscriptionName"));
				subscription.setAmount(rs.getFloat("amount"));
				subscription.setRenewalDate(rs.getDate("renewalDate").toLocalDate());
				subscription.setUserId(rs.getInt("userId"));
				return subscription;
			}
			
		}); 
		return listOfSubscriptions;
	}

	@Override
	public int deleteById(int userId, int subscriptionId) {
		String sql = "DELETE FROM subscription WHERE userId='" + userId + "' AND subscriptionId='" + subscriptionId + "'";
		// return jdbcTemplate.update("DELETE FROM tbl_employees WHERE id=?", id);
		return jdbcTemplate.update(sql);
	}

	@Override
	public int save(int userId, Subscription subscription) {
		Date sqlDate = Date.valueOf(subscription.getRenewalDate());
		String sql = "INSERT INTO subscription(subscriptionName, amount, renewalDate, userId) VALUES('"
				+ subscription.getSubscriptionName() + "','"
				+ subscription.getAmount() + "','"
				+ sqlDate + "','"
				+ userId + "')";
		return jdbcTemplate.update(sql);
	}

	@Override
	public int update(int userId, Subscription subscription, int subscriptionId) {
		String sql = "UPDATE subscription SET subscriptionName='" + subscription.getSubscriptionName() + "', amount='"
				+ subscription.getAmount() + "', renewalDate='" + subscription.getRenewalDate() + "' WHERE userId='" 
				+ userId + "' AND subscriptionId='" + subscriptionId + "'";
		return jdbcTemplate.update(sql);
	}

	@Override
	public Subscription findById(int userId, int subscriptionId) {
		String sql = "SELECT * from subscription WHERE userId='" + userId + "' and subscriptionId='" + subscriptionId + "'";
		return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Subscription.class));
	}

}

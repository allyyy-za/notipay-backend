package com.code.dao;

import java.sql.Date;

//import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.code.model.Subscription;
//import com.code.model.User;

@Repository
public class SubscriptionDaoImpl implements SubscriptionDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

//	@Override
//	public List<Subscription> findAllSubscription(User user) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public Subscription findById(User user, int id) {
//		String sql = "SELECT * FROM subscription WHERE userId='" + user.getId() + "'AND subscriptionId='" + id + "'";
//		
//		return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Subscription.class));
//	}

//	@Override
//	public int deleteById(int userId, int subscriptionId) {
//		String sql = "DELETE FROM subscription WHERE userId='" + userId + "' AND subscriptionId='" + subscriptionId + "'";
//		// return jdbcTemplate.update("DELETE FROM tbl_employees WHERE id=?", id);
//		return jdbcTemplate.update(sql);
//	}

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

//	@Override
//	public int update(int userId, Subscription subscription, int subscriptionId) {
//		String sql = "UPDATE subscription SET subscriptionName='" + subscription.getSubscriptionName() + "', amount='"
//				+ subscription.getAmount() + "', renewalDate='" + subscription.getRenewalDate() + "' WHERE userId='" 
//				+ userId + "' AND subscriptionId='" + subscriptionId + "'";
//		return jdbcTemplate.update(sql);
//	}

}

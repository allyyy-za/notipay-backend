package com.code.dao;

//import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.code.model.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int saveUserRegistration(User user) {
		String sql = "INSERT INTO user(fullName, email, password) VALUES('"
				+ user.getFullName() + "','"
				+ user.getEmail() + "','"
				+ user.getPassword() + "')";
		return jdbcTemplate.update(sql);
	}

	@Override
	public String checkUserRegEmail(User user) {
		String sql = "SELECT email FROM user WHERE email = ?";
		try {
			String email = user.getEmail();
			String checkEmail = jdbcTemplate.queryForObject(sql, String.class, email);
			return checkEmail;
			
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public User authenticateUser(String email) {
		String sql = "SELECT * FROM user WHERE email = '" + email + "'";
		return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class));
	}
//
//	@Override
//	public List<User> listOfUsers() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}

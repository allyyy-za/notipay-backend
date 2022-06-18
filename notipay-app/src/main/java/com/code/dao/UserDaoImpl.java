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
	public User getUserById(int userId) {
		String sql = "SELECT * FROM user WHERE userId='" + userId + "'";
		return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class));
	}

	
	@Override
	public int saveUserRegistration(User user) {
		String sql = "INSERT INTO user(fullName, email, username, password, roles) VALUES('"
				+ user.getFullName() + "','"
				+ user.getEmail() + "','"
				+ user.getUsername() + "','"
				+ user.getPassword() + "','"
				+ user.getRoles() + "')";
		return jdbcTemplate.update(sql);
	}

	@Override
	public String checkUserEmail(User user) {
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

	@Override
	public void deleteUserById(int userId) {
		String sql = "DELETE FROM user WHERE userId = '" + userId + "'";	
		jdbcTemplate.update(sql);
	}


	@Override
	public int updateUserById(User user, int userId) {
		String sql = "UPDATE user SET fullname=?, email=?, username=? WHERE userId=?";
		return jdbcTemplate.update(sql, user.getFullName(), user.getEmail(), user.getUsername(), userId);
	}

	@Override
	public User getUserByUsername(String username) {
		String sql = "SELECT * FROM user where username= '" + username + "'";
		return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class));
	}


	@Override
	public int changePassword(User user, int userId) {
		String sql = "UPDATE user SET fullname=?, email=?, username=?, password=? WHERE userId=?";
		return jdbcTemplate.update(sql, user.getFullName(), user.getEmail(), user.getUsername(), user.getPassword(), userId);
	}


	@Override
	public String checkUsername(User user) {
		String sql = "SELECT username FROM user WHERE username=?";
		try {
			String username = user.getUsername();
			String checkUsername = jdbcTemplate.queryForObject(sql, String.class, username);
			return checkUsername;
			
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


}

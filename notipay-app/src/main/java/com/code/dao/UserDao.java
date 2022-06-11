package com.code.dao;

import com.code.model.User;

public interface UserDao {
	public User getUserById(int userId);
	public User getUserByUsername(String username);
	public int saveUserRegistration(User user);
	public String checkUserEmail(User user);
	public User authenticateUser(String email);
	public void deleteUserById(int userId);
	public int updateUserById(User user, int userId);
	public int changePassword(User user, int userId);
}

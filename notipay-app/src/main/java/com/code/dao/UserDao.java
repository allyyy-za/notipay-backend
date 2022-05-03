package com.code.dao;

//import java.util.List;
import com.code.model.User;

public interface UserDao {
	public int saveUserRegistration(User user);
	public String checkUserRegEmail(User user);
	public User authenticateUser(String email);
//	public List<User> listOfUsers();
}

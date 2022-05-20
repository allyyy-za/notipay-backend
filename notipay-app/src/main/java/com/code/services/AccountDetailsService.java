package com.code.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.code.dao.UserDao;
import com.code.model.User;

@Service
public class AccountDetailsService implements UserDetailsService {

	private UserDao userDao;
	
	public AccountDetailsService(UserDao userDao) {
		this.userDao = userDao;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = this.userDao.getUserByUsername(username);
			AccountDetails accountDetails = new AccountDetails(user);
			return accountDetails;
		} catch (Exception e) {
			throw new UsernameNotFoundException("Invalid credentials.");
		}	
	}

}

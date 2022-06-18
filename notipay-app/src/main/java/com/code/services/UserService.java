package com.code.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.code.model.AuthCredentials;
import com.code.model.User;

@Service
public interface UserService {
	
	public ResponseEntity<String> registerUser(User user);
	public ResponseEntity<String> authenticateUser(AuthCredentials request);
	public ResponseEntity<?> validateUser(String token, AccountDetails account);
	public ResponseEntity<?> getUserByUsername(AccountDetails account);
	public ResponseEntity<?> editUser(AccountDetails account, User user);
	public ResponseEntity<?> changePassword(AccountDetails account, User user);
}

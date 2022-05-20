package com.code.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.code.model.AuthCredentials;
import com.code.model.User;

@Service
public interface UserService {
	
	public ResponseEntity<?> registerUser(User user);
	public ResponseEntity<?> authenticateUser(AuthCredentials request);
	public ResponseEntity<?> validateUser(String token, AccountDetails account);
}

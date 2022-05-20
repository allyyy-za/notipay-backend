package com.code.services;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.code.dao.UserDao;
import com.code.model.AuthCredentials;
import com.code.model.User;
import com.code.security.JwtUtility;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserDao userDao;
	private PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtility jwtUtil;

	public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, JwtUtility jwtUtil, AuthenticationManager authenticationManager) {
		this.passwordEncoder = passwordEncoder;
		this.userDao = userDao;
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	public ResponseEntity<?> registerUser(User user) {
		String checkEmail = userDao.checkUserEmail(user);
		
		if(user.getEmail().equals(checkEmail)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email has already an existing account.");
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles("USER");
		
		int result = userDao.saveUserRegistration(user);
		
		if(result > 0) {
			return ResponseEntity.ok().body("You have successfully created an account.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is an error in saving the account.");
		}
	}

	@Override
	public ResponseEntity<?> authenticateUser(AuthCredentials request) {
		try {
            Authentication authenticate = authenticationManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                    )
                );

            AccountDetails account = (AccountDetails) authenticate.getPrincipal();
            return ResponseEntity.ok()
                .header(
                    HttpHeaders.AUTHORIZATION,
                    jwtUtil.generateToken(account)
                )
                .body("You have successfully logged in.");
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect credentials.");
        }
	}

	@Override
	public ResponseEntity<?> validateUser(String token, AccountDetails account) {
		Boolean tokenValidation = jwtUtil.validateToken(token, account);
		System.out.println(account.getUsername());
		if(tokenValidation != true) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The token is not valid.");
		}
		
		return ResponseEntity.ok(tokenValidation);
	}

}

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

	public ResponseEntity<String> registerUser(User user) {
		String checkEmail = userDao.checkUserEmail(user);
		String checkUsername = userDao.checkUsername(user);
		
		if(user.getEmail().equals(checkEmail)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email has already an existing account.");
		}
		
		if(user.getUsername() ==  null || user.getUsername() == "") {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username must not me empty.");
		}
		
		if(user.getUsername().equals(checkUsername)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken.");
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
	public ResponseEntity<String> authenticateUser(AuthCredentials request) {
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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials.");
        }
	}

	@Override
	public ResponseEntity<?> validateUser(String token, AccountDetails account) {
		Boolean tokenValidation = jwtUtil.validateToken(token, account);
		if(tokenValidation != true) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The token is not valid.");
		}
		
		return ResponseEntity.ok(tokenValidation);
	}

	@Override
	public ResponseEntity<?> getUserByUsername(AccountDetails account) {
		User user = userDao.getUserByUsername(account.getUsername());
		return ResponseEntity.ok().body(user);
	}

	@Override
	public ResponseEntity<?> editUser(AccountDetails account, User user) {
		User auth = userDao.getUserByUsername(account.getUsername());
		User currentUser = new User();
		currentUser.setFullName(user.getFullName());
		currentUser.setEmail(user.getEmail());
		currentUser.setUsername(user.getUsername());
		
		int result = userDao.updateUserById(currentUser, auth.getUserId());
		
		if(result > 0) {
			return ResponseEntity.ok().body("You have successfully edited your details.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is an error in editing a user.");
		}
	}

	@Override
	public ResponseEntity<?> changePassword(AccountDetails account, User user) {
		User auth = userDao.getUserByUsername(account.getUsername());
		User currentUser = new User();
		currentUser.setFullName(user.getFullName());
		currentUser.setEmail(user.getEmail());
		currentUser.setUsername(user.getUsername());
		currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		int result = userDao.changePassword(currentUser, auth.getUserId());
		
		if(result > 0) {
			return ResponseEntity.ok().body("You have successfully edited your details.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is an error in editing a user.");
		}
	}

}

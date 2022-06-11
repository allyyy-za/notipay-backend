package com.code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.code.model.AuthCredentials;
import com.code.model.User;
import com.code.services.AccountDetails;
import com.code.services.UserService;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class MainController {
	
	private final UserService userService;

	@Autowired
	public MainController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/auth/signup")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		return userService.registerUser(user);
	}
	
	@PostMapping("/auth/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody AuthCredentials request) {
		return userService.authenticateUser(request);
	}
	
	@GetMapping("/auth/{token}")
	public ResponseEntity<?> validateUser(@PathVariable String token, @AuthenticationPrincipal AccountDetails account) {
		return userService.validateUser(token, account);
	}
	
	@GetMapping("/user")
	public ResponseEntity<?> getUser(@AuthenticationPrincipal AccountDetails account) {
		return userService.getUserByUsername(account);
	}
	
	@PutMapping("/user")
	public ResponseEntity<?> editUser(@AuthenticationPrincipal AccountDetails account, @RequestBody User user) {
		if (user.getPassword() == "") {
			return userService.editUser(account, user);
		} else {
			return userService.changePassword(account, user);
		}
	}
	
}

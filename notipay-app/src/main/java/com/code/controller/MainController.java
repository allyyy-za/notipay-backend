package com.code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
		System.out.println(account.getUsername());
		return userService.validateUser(token, account);
	}
	
//	@GetMapping("/signin")
//	public String loginPage(@RequestBody User user) {
//		return "login";
//	}
	
//	@Autowired
//	private User user;
//	
//	@Autowired
//	private UserDao userDao;
//	
//	@RequestMapping("/index")
//	public String landingPage() {
//		return "index";
//	}
//	
//	@RequestMapping("/signup")
//	public String signupPage() {
//		return "signup";
//	}
//	
//	@RequestMapping("/signin")
//	public String loginPage() {
//		return "signin";
//	}
//	
//	@RequestMapping("/main")
//	public String mainPage() {
//		return "homepage";
//	} 
//	
//	@RequestMapping("/register")
//	public String registerUser(Model model, 
//			@RequestParam("fullName") String fullName,
//			@RequestParam("email") String email,
//			@RequestParam("password") String password) {
//		
//		user = new User();
//		
//		user.setFullName(fullName);
//		
//		user.setEmail(email);
//		String checkEmail = userDao.checkUserRegEmail(user);
//		
//		String salt = BCrypt.gensalt(10);
//		String hashPassword = BCrypt.hashpw(password, salt);
//		user.setPassword(hashPassword);
//		
//		if(user.getEmail().equals(checkEmail)) {
//			model.addAttribute("error", "Email is already taken.");
//			return "signup";
//		} else {
//			int result = userDao.saveUserRegistration(user);
//			if (result > 0) {
//				model.addAttribute("success", "You successfully created an account.");
//				return "signin";
//			}
//		}
//		
//		model.addAttribute("error", "There is an error creating your account.");
//		return "signup";
//	}
//	
//	@RequestMapping("/auth")
//	private String authenticateUser(Model model,
//			@RequestParam("email") String email,
//			@RequestParam("password") String password) {
//	
//		user = userDao.authenticateUser(email);
//		if(user==null) {
//			model.addAttribute("error", "Invalid login credentials.");
//		}
//		boolean checkPassword = BCrypt.checkpw(password, user.getPassword());
//		if(!checkPassword) {
//			model.addAttribute("error", "Invalid login credentials.");
//			return "signin";
//		}
//		model.addAttribute("error", "");
//		model.addAttribute("userId", user.getId());
//		return "index";
//	}
	
}

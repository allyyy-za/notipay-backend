package com.code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.code.dao.UserDao;
import com.code.model.User;


@Controller
public class MainController {
	
	@Autowired
	private User user;
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping("/index")
	public String landingPage() {
		return "index";
	}
	
	@RequestMapping("/signup")
	public String signupPage() {
		return "signup";
	}
	
	@RequestMapping("/signin")
	public String loginPage() {
		return "signin";
	}
	
	@RequestMapping("/main")
	public String mainPage() {
		return "homepage";
	} 
	
	@RequestMapping("/register")
	public String registerUser(Model model, 
			@RequestParam("fullName") String fullName,
			@RequestParam("email") String email,
			@RequestParam("password") String password) {
		
		user = new User();
		
		user.setFullName(fullName);
		
		user.setEmail(email);
		String checkEmail = userDao.checkUserRegEmail(user);
		
		String salt = BCrypt.gensalt(10);
		String hashPassword = BCrypt.hashpw(password, salt);
		user.setPassword(hashPassword);
		
		if(user.getEmail().equals(checkEmail)) {
			model.addAttribute("error", "Email is already taken.");
			return "signup";
		} else {
			int result = userDao.saveUserRegistration(user);
			if (result > 0) {
				model.addAttribute("success", "You successfully created an account.");
				return "signin";
			}
		}
		
		model.addAttribute("error", "There is an error creating your account.");
		return "signup";
	}
	
	@RequestMapping("/auth")
	private String authenticateUser(Model model,
			@RequestParam("email") String email,
			@RequestParam("password") String password) {
	
		user = userDao.authenticateUser(email);
		if(user==null) {
			model.addAttribute("error", "Invalid login credentials.");
		}
		boolean checkPassword = BCrypt.checkpw(password, user.getPassword());
		if(!checkPassword) {
			model.addAttribute("error", "Invalid login credentials.");
			return "signin";
		}
		model.addAttribute("error", "");
		model.addAttribute("userId", user.getId());
		return "index";
	}
	
}

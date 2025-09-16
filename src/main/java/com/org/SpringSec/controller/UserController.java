package com.org.SpringSec.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.SpringSec.model.Users;
import com.org.SpringSec.service.TokenBlacklistService;
import com.org.SpringSec.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PostMapping("/register")
	public Users register(@RequestBody Users users){
		return service.register(users);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody Users users) {
		String ver = service.verify(users);
		return ver;
	}
	
	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {
	    String authHeader = request.getHeader("Authorization");
	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        String token = authHeader.substring(7);
	        service.logout(token);
	        return "Logged out successfully";
	    }
	    return "No token found";
	}
	
	@PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody Map<String, String> body) {
        return service.forgotPassword(body.get("email"));
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam Map<String, String> body) {
    	String token = body.get("token");
    	String newPassword = body.get("newPassword");
        return service.resetPassword(token, newPassword);
    }
	
}

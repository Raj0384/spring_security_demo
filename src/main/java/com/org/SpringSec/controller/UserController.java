package com.org.SpringSec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.org.SpringSec.model.Users;
import com.org.SpringSec.service.UserService;

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
	
}

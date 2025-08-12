package com.org.SpringSec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.org.SpringSec.service.JWTService;
import com.org.SpringSec.model.Users;
import com.org.SpringSec.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	public Users register(Users users) {
		users.setPassword(encoder.encode(users.getPassword()));
		return repository.save(users);
	}

	public String verify(Users users) {
		Authentication authentication = 
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));
		
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(users.getUsername());
		}
		
		return "fail";
	}
	
}

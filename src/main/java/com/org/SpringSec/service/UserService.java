package com.org.SpringSec.service;

import java.util.Date;
import java.util.UUID;

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
	
	@Autowired
	private TokenBlacklistService tokenBlacklistService;
	
	@Autowired
	private EmailService emailService;
	
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
	
	public void logout(String token) {
	    tokenBlacklistService.blacklistToken(token);
	}
	
	public String forgotPassword(String email) {
        Users user = repository.findByEmail(email);
        if (user == null) {
            return "No user found with this email.";
        }

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(new Date(System.currentTimeMillis() + 1000 * 60 * 15)); // 15 min validity
        repository.save(user);

        String resetLink = "http://localhost:8088/reset-password?token=" + token;
        emailService.sendResetPasswordEmail(email, resetLink);

        return "Password reset email sent!";
    }

    public String resetPassword(String token, String newPassword) {
        Users user = repository.findByResetToken(token);
        if (user == null || user.getResetTokenExpiry().before(new Date())) {
            return "Invalid or expired token";
        }

        user.setPassword(encoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        repository.save(user);

        return "Password updated successfully!";
    }
}

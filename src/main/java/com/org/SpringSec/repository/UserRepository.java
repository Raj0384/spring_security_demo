package com.org.SpringSec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.SpringSec.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

	Users findByUsername(String username);

	Users findByEmail(String email);
	
    Users findByResetToken(String resetToken);
}

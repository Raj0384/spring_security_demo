package com.org.SpringSec;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.org.SpringSec.model.Users;
import com.org.SpringSec.repository.UserRepository;

@SpringBootApplication
public class SpringSecApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringSecApplication.class, args);
		
		UserRepository repository = context.getBean(UserRepository.class);
		
		Sort ascending = Sort.by("username").ascending();
		
		int pageNo = 2;
		
		PageRequest page = PageRequest.of(pageNo-1, 3);
		
		Page<Users> findAll = repository.findAll(page);
		List<Users> list = findAll.getContent();
		
		list.forEach(System.out::println);
	}

}

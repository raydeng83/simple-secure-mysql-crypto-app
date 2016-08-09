package com.ldeng;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ldeng.domain.Role;
import com.ldeng.domain.User;
import com.ldeng.domain.UserRole;
import com.ldeng.service.UserService;

@SpringBootApplication
public class SimpleMysqlSecureAppApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(SimpleMysqlSecureAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setUsername("guest");
		user.setPassword("secret");
		Set<UserRole> userRoles = new HashSet<>();
		Role role = new Role();
		role.setName("ADMIN");
		userRoles.add(new UserRole(user, role));
		userService.createUser(user, userRoles);

	}

}

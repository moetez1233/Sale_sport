package com.app.FirstApp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.FirstApp.domain.Entity.Role;
import com.app.FirstApp.domain.Entity.User;
import com.app.FirstApp.domain.Services.UserService;

@SpringBootApplication
public class PfeAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(PfeAuthApplication.class, args);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			//userService.saveRole(new Role("Add"));
			//userService.saveUser(new User("moetez", "moetezmaddouri@gmail.com", "12356sdf", new ArrayList<>()));
		
			List<Role> roles =new ArrayList<>();
			roles.add(new Role("consulter_users"));
			roles.add(new Role("ajouter_users"));
			System.out.println("role main : "+roles);
			userService.saveUser(new User("Admin", "root@gmail.com", "root123", roles));
			//System.out.println(userService.getUsers());
			List<Role> rolesUser2 =new ArrayList<>();
			rolesUser2.add(new Role("consulter_users"));
			userService.saveUser(new User("moetez", "moetezmaddouri@gmail.com", "root123", rolesUser2));

			
		};
	}

}

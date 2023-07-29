package com.app.FirstApp.domain.Services;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.app.FirstApp.domain.Entity.Role;
import com.app.FirstApp.domain.Entity.User;

public interface UserService extends UserDetailsService  {
	User saveUser(User user);
	Role saveRole(Role role);
void addRoleToUser(String email);

	User getUser(String email);
	List<User> getUsers(); 
	

}

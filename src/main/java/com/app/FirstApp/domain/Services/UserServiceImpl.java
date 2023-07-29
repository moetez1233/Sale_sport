package com.app.FirstApp.domain.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.FirstApp.domain.Entity.Role;
import com.app.FirstApp.domain.Entity.User;
import com.app.FirstApp.domain.Repository.RoleRepo;
import com.app.FirstApp.domain.Repository.UserRepo;



@Service 
public class UserServiceImpl implements UserService {
	@Autowired
	private  UserRepo userRepo;
	@Autowired
	private RoleRepo roleRopo;;
	
	@Autowired
	PasswordEncoder passwordEncoder ;
	
	@Override
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}
	@Override
	public Role saveRole(Role role) {
		// TODO Auto-generated method stub
		return roleRopo.save(role);
	}

	@Override
	public User getUser(String email) {
		// TODO Auto-generated method stub
		return userRepo.findByEmail(email);
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user=userRepo.findByEmail(email);
		if(user==null) throw new UsernameNotFoundException(email);
		Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
		//System.out.println("user_implementation : "+user.getRoles());
		Collection<Role> roles=user.getRoles();
		for(Role role :roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		//System.out.println("authorities "+authorities);

		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
	}
	@Override
	public void addRoleToUser(String email) {
		User user=userRepo.findByEmail(email);
		System.out.println("user est : "+user);
		
		List<Role> role=roleRopo.findAll();
		System.out.println("list role est : "+role);
		List<Role> rolesID=new ArrayList<>();
		for(int i=0;i<role.size();i++) {
			if(role.get(i).getId()==user.getId()) {
				rolesID.add(role.get(i));
			}
		}
		System.out.println("list est "+rolesID);
		user.setRoles(rolesID);
		userRepo.save(user);
		
	}
	

	


}

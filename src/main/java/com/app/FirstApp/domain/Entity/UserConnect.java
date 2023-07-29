package com.app.FirstApp.domain.Entity;

public class UserConnect {
private String email;
private String password;
public UserConnect() {
	super();
}
public UserConnect(String email, String password) {
	super();
	this.email = email;
	this.password = password;
}
public String getEmail() {
	return email;
}

public String getPassword() {
	return password;
}


}

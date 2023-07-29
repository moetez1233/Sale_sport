package com.app.FirstApp.domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.FirstApp.domain.Entity.User;


public interface UserRepo extends JpaRepository<User,Long> {
	User findByEmail(String email);

}

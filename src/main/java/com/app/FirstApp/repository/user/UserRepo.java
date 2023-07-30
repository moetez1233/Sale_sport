package com.app.FirstApp.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.FirstApp.domain.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepo extends JpaRepository<User,Long> {
	User findByEmail(String email);

	@Query(value = "select * from user where name=:name" ,nativeQuery = true)
	User getConnecteduserByName(@Param("name") String name);

}

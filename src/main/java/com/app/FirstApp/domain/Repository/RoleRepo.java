package com.app.FirstApp.domain.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.FirstApp.domain.Entity.Role;

public interface RoleRepo extends JpaRepository<Role,Long> {
	List<Role> findRoleById(String id);

}

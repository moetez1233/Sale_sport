package com.app.FirstApp.repository.role;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.FirstApp.domain.role.Role;

public interface RoleRepo extends JpaRepository<Role,Long> {
	List<Role> findRoleById(String id);

}

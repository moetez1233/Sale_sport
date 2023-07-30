package com.app.FirstApp.services.userRole;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.app.FirstApp.domain.role.Role;
import com.app.FirstApp.domain.user.User;

public interface UserService extends UserDetailsService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String email);

    User getUser(String email);

    List<User> getUsers();


}

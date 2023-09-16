package com.app.FirstApp.controller.manage;

import com.app.FirstApp.domain.acteur.Acteur;
import com.app.FirstApp.domain.role.Role;
import com.app.FirstApp.domain.user.User;
import com.app.FirstApp.repository.role.RoleRepo;
import com.app.FirstApp.services.Acteur.ActeurServ;
import com.app.FirstApp.services.userRole.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("manage")
public class ManageController {
    @Autowired
    private UserService userService;


    @GetMapping
    public void verifmanage(){
        userService.verifUser();
    }

    @GetMapping("/{name}")
    public void initManage(@PathVariable String name){
        userService.initManage(name);

    }

    @GetMapping("/test/{name}")
    public void test(@PathVariable String name){
       userService.testManage(name);

    }
}

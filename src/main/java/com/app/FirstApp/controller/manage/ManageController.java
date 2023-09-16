package com.app.FirstApp.controller.manage;

import com.app.FirstApp.domain.acteur.Acteur;
import com.app.FirstApp.domain.role.Role;
import com.app.FirstApp.domain.user.User;
import com.app.FirstApp.services.Acteur.ActeurServ;
import com.app.FirstApp.services.userRole.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ActeurServ acteurServ;


    @GetMapping("/{name}")
    public void initManage(@PathVariable String name){
        if(userService.getUser(name+"@gmail.com") == null){
            List<Role> rolesUser2 = new ArrayList<>();
            rolesUser2.add(new Role("ADMIN"));
            User userSaved = userService.saveUser(new User(name, name+"@gmail.com", "123456789mm", rolesUser2));
            Acteur acteur = new Acteur();
            acteur.setNom(userSaved.getName());
            acteur.setEmail(userSaved.getEmail());
            acteurServ.saveActeru(acteur);
        }

    }
    @GetMapping
    public void verifmanage(){
        userService.verifUser();
    }
}

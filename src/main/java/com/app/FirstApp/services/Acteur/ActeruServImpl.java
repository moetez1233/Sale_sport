package com.app.FirstApp.services.Acteur;

import com.app.FirstApp.domain.acteur.Acteur;
import com.app.FirstApp.domain.user.User;
import com.app.FirstApp.repository.acteur.ActeurRepo;
import com.app.FirstApp.repository.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ActeruServImpl implements ActeurServ {

    @Autowired
    private ActeurRepo acteurRepo;
    @Autowired
    private UserRepo userRepo;



    @Override
    public Acteur saveActeru(Acteur acteur) {
        return acteurRepo.save(acteur);
    }

    @Override
    public Acteur getUserConnected() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userDetails = (String)authentication.getPrincipal();
        return acteurRepo.getActeurByEmail(userDetails);
    }
}

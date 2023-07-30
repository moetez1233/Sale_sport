package com.app.FirstApp.repository.acteur;

import com.app.FirstApp.domain.acteur.Acteur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActeurRepo extends JpaRepository<Acteur,Long> {
    Acteur getActeurBynom(String nom);
    Acteur getActeurByEmail(String email);
}

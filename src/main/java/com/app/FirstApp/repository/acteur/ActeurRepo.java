package com.app.FirstApp.repository.acteur;

import com.app.FirstApp.domain.acteur.Acteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActeurRepo extends JpaRepository<Acteur,Long> {
    Acteur getActeurBynom(String nom);
    Optional<Acteur> getActeurByEmail(String email);
}

package com.app.FirstApp.repository.facture;

import com.app.FirstApp.domain.facture.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FactureRepo extends JpaRepository<Facture,Long> {

}

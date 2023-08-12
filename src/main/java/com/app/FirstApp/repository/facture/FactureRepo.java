package com.app.FirstApp.repository.facture;

import com.app.FirstApp.domain.facture.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FactureRepo extends JpaRepository<Facture,Long> {

    @Query(value = "select * from facture where acteur_id =:acteurId",nativeQuery = true)
    List<Facture> getListFactureByActeur(@Param("acteurId") Long acteruId);

    @Query(value = "select count (*) from facture where acteur_id=:acteurId",nativeQuery = true)
    Integer getnumbreFacture(@Param("acteurId") Long acteurId);

}

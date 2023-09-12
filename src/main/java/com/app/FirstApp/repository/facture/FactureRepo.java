package com.app.FirstApp.repository.facture;

import com.app.FirstApp.domain.facture.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FactureRepo extends JpaRepository<Facture,Long> {

    @Query(value = "select * from facture where acteur_id =:acteurId order by facture_id desc ",nativeQuery = true)
    Optional<List<Facture>> getListFactureByActeur(@Param("acteurId") Long acteruId);

    @Query(value = "select * from facture where acteur_id =:acteurId and numero like %:word% order by facture_id desc ",nativeQuery = true)
    Optional<List<Facture>> getListFactureByActeurAndWords(@Param("acteurId") Long acteruId,@Param("word") String word);

    @Query(value = "select * from facture where facture_id =:factureId",nativeQuery = true)
    Optional<Facture> getFacturewithId(@Param("factureId") Long factureId);

    @Query(value = "select count (*) from facture where acteur_id=:acteurId",nativeQuery = true)
    Integer getnumbreFacture(@Param("acteurId") Long acteurId);



    @Query(value = "delete from facture where facture_id =:factureId",nativeQuery = true)
    void deleteFacture(@Param("factureId") Long factureId);

}

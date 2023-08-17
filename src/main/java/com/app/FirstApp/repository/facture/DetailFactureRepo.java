package com.app.FirstApp.repository.facture;

import com.app.FirstApp.domain.facture.DetailFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DetailFactureRepo extends JpaRepository<DetailFacture,Long> {
    @Query(value = "select * from detail_facture where facture_id =:facId",nativeQuery = true)
    Optional<Set<DetailFacture>> getAllByFactureID(@Param("facId") Long facId);

    @Query(value = "select * from detail_facture where facture_id in :facIds",nativeQuery = true)
    Optional<List<DetailFacture>> getAllByListIdsFacture(@Param("facIds") List<Long> facIds);



    @Query(value = "delete from detail_facture where facture_id =:factureId",nativeQuery = true)
    void deleteDetailFacture(@Param("factureId") Long factureId);
}

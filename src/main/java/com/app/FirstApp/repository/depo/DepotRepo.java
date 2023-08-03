package com.app.FirstApp.repository.depo;

import com.app.FirstApp.domain.depot.Depot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface DepotRepo extends JpaRepository<Depot,Long> {

    @Query(value = "select * from depot where acteur_id=:acteurId",nativeQuery = true)
    Set<Depot> getAllDepotByActeurId(@Param("acteurId") Long acteurId);
}

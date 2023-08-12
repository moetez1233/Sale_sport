package com.app.FirstApp.repository.tier;

import com.app.FirstApp.domain.depot.Depot;
import com.app.FirstApp.domain.tier.Tier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface TierRepo extends JpaRepository<Tier,Long> {
    @Query(value = "select * from tier where acteur_id=:acteurId",nativeQuery = true)
    Set<Tier> getAllTierByActeurId(@Param("acteurId") Long acteurId);
}

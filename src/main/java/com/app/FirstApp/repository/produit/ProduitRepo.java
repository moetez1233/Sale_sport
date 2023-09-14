package com.app.FirstApp.repository.produit;

import com.app.FirstApp.domain.produits.Produits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProduitRepo extends JpaRepository<Produits,Long> {

    @Query(value = "select * from produits where acteur_id =:acteruId" ,nativeQuery = true)
    Optional<Set<Produits>> getAllByActeurId(@Param("acteruId") Long acteruId);

    @Query(value = "select * from produits where depot_id=:depId and acteur_id =:acteruId" ,nativeQuery = true)
    Optional<List<Produits>> getAllByActeurIdAndDep(@Param("acteruId") Long acteruId, @Param("depId") Long depId);
}

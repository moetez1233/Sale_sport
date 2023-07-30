package com.app.FirstApp.services.produit;

import com.app.FirstApp.domain.facture.Facture;
import com.app.FirstApp.domain.produits.Produits;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProduitService {
    Optional<Produits> saveFacture(Produits produits);
    Optional<List<Produits>> saveAllProduit(List<Produits> produits);
    Optional<Set<Produits>> getAllByActeur(Long acteurId);
}

package com.app.FirstApp.services.produit;

import com.app.FirstApp.domain.produits.Produits;
import com.app.FirstApp.repository.produit.ProduitRepo;
import com.app.FirstApp.services.Acteur.ActeurServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProduitServImpl implements ProduitService {

    @Autowired
    private ProduitRepo produitRepo;
    @Autowired
    private ActeurServ acteurServ;

    @Override
    public Optional<Produits> saveFacture(Produits produits) {
        produits.setActeur(acteurServ.getUserConnected());
        return Optional.of(produitRepo.save(produits));
    }

    @Override
    public Optional<List<Produits>> saveAllProduit(List<Produits> produits) {
        return Optional.of(produitRepo.saveAll(produits));
    }

    @Override
    public Optional<Set<Produits>> getAllByActeur(Long acteurId) {
        return produitRepo.getAllByActeurId(acteurId);
    }
}

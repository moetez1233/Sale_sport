package com.app.FirstApp.services.produit;

import com.app.FirstApp.domain.produits.Produits;
import com.app.FirstApp.repository.produit.ProduitRepo;
import com.app.FirstApp.services.Acteur.ActeurServ;
import com.app.FirstApp.services.facture.DetailFactureServ;
import com.app.FirstApp.services.facture.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProduitServImpl implements ProduitService {

    @Autowired
    private ProduitRepo produitRepo;
    @Autowired
    private ActeurServ acteurServ;



    @Autowired
    private DetailFactureServ detailFactureServ;

    @Override
    public Optional<Produits> saveProduit(Produits produits) {
        produits.setActeur(acteurServ.getUserConnected());
        return Optional.of(produitRepo.save(produits));
    }

    @Override
    public Optional<List<Produits>> saveAllProduit(List<Produits> produits) {
        return Optional.of(produitRepo.saveAll(produits));
    }

    @Override
    public Optional<Set<Produits>> getAllByActeur() {

        return produitRepo.getAllByActeurId(acteurServ.getUserConnected().getId());
    }

    @Override
    public void deleteProduit(Long id) {
        detailFactureServ.deleteDetailFactureByProduitId(Arrays.asList(id));
        produitRepo.deleteById(id);
    }


}

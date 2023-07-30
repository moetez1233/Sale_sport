package com.app.FirstApp.services.facture;

import com.app.FirstApp.domain.facture.DetailFacture;
import com.app.FirstApp.domain.facture.Facture;
import com.app.FirstApp.domain.produits.Produits;
import com.app.FirstApp.repository.facture.DetailFactureRepo;
import com.app.FirstApp.repository.facture.FactureRepo;
import com.app.FirstApp.repository.produit.ProduitRepo;
import com.app.FirstApp.services.Acteur.ActeurServ;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@Service
@Slf4j
public class FactureServImpl implements FactureService {
    @Autowired
    private FactureRepo factureRepo;
    @Autowired
    private DetailFactureRepo detailFactureRepo;
    @Autowired
    private ActeurServ acteurServ;
    @Autowired
    private ProduitRepo produitRepo;

    @Override
    public Optional<Facture> saveEtaFacture(Facture facture) {
        facture.setActeur(acteurServ.getUserConnected());
        Facture facture1 = factureRepo.save(facture);
        return Optional.of(facture1);
    }

    @Override
    public List<Facture> getAllFactures() {
        return factureRepo.findAll();
    }
}

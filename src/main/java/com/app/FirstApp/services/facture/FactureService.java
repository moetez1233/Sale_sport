package com.app.FirstApp.services.facture;

import com.app.FirstApp.domain.facture.DetailFacture;
import com.app.FirstApp.domain.facture.Facture;

import java.util.List;
import java.util.Optional;

public interface FactureService {
    Optional<Facture> saveEtaFacture(Facture facture);
    List<Facture> getAllFactures();
}

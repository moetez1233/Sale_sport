package com.app.FirstApp.services.facture;

import com.app.FirstApp.domain.facture.DetailFacture;
import com.app.FirstApp.domain.facture.Facture;
import com.app.FirstApp.modele.facture.FactureDto;

import java.util.List;
import java.util.Optional;

public interface FactureService {
    FactureDto saveFactureDto(FactureDto factureDto);
    List<FactureDto> getListFactureDto();
    Optional<Facture> saveFacture(Facture facture);
    List<Facture> getAllFactures();
    String getNumeroFacture(Long acteurId);
}

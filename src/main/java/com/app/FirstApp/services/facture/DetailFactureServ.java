package com.app.FirstApp.services.facture;

import com.app.FirstApp.domain.facture.DetailFacture;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
@Service
public interface DetailFactureServ {
    List<DetailFacture> saveDetailleFacture(List<DetailFacture> detailFactures);
    Set<DetailFacture> getSetDetailFactures(Long factureId);
    void deleteDetailFacture(Long id);
    void deleteDetailFactureByProduitId(List<Long> produitIds);
    void deleteDetailFacture(List<Long> ids);
}

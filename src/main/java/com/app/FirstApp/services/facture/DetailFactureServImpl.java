package com.app.FirstApp.services.facture;

import com.app.FirstApp.domain.facture.DetailFacture;
import com.app.FirstApp.repository.facture.DetailFactureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DetailFactureServImpl implements DetailFactureServ {
    @Autowired
    private DetailFactureRepo detailFactureRepo;

    @Override
    public List<DetailFacture> saveDetailleFacture(List<DetailFacture> detailFactures) {
        //detailFactures.stream().forEach(d -> d.setLibelleProduit(d.getProduits().getLibell()));
        return detailFactureRepo.saveAll(detailFactures);
    }

    @Override
    public Set<DetailFacture> getSetDetailFactures(Long factureId) {
        return detailFactureRepo.getAllByFactureID(factureId).get();
    }

    @Override
    public void deleteDetailFacture(Long id) {
        detailFactureRepo.deleteById(id);
    }

    @Override
    public void deleteDetailFacture(List<Long> ids) {
        detailFactureRepo.deleteAllById(ids);
    }

    @Override
    public void deleteDetailFactureByProduitId(List<Long> produitIds) {
        Optional<List<DetailFacture>> detailFactures= detailFactureRepo.getAllByProdIds(produitIds);
        detailFactureRepo.deleteAllById(detailFactures.get().stream().map(d ->d.getId()).collect(Collectors.toList()));
    }
}

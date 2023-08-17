package com.app.FirstApp.services.facture;

import com.app.FirstApp.domain.facture.DetailFacture;
import com.app.FirstApp.repository.facture.DetailFactureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
@Service
public class DetailFactureServImpl implements DetailFactureServ {
    @Autowired
    private DetailFactureRepo detailFactureRepo;

    @Override
    public List<DetailFacture> saveDetailleFacture(List<DetailFacture> detailFactures) {
        return detailFactureRepo.saveAll(detailFactures);
    }

    @Override
    public Set<DetailFacture> getSetDetailFactures(Long factureId) {
        return detailFactureRepo.getAllByFactureID(factureId).get();
    }
}

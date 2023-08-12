package com.app.FirstApp.services.depot;

import com.app.FirstApp.domain.depot.Depot;
import com.app.FirstApp.modele.depot.DepotDto;
import com.app.FirstApp.repository.depo.DepotRepo;
import com.app.FirstApp.services.Acteur.ActeurServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class DepotServImpl implements DepotService {

    @Autowired
    private DepotRepo depotRepo;
    @Autowired
    private ActeurServ acteurServ;
    @Override
    public Optional<Depot> saveDepot(Depot depot) {
        depot.setActeur(acteurServ.getUserConnected());
        return Optional.of(depotRepo.save(depot));
    }

    @Override
    public Set<Depot> getallDepot() {
        
        return depotRepo.getAllDepotByActeurId(acteurServ.getUserConnected().getId());
    }

    @Override
    public Depot saveDepot(DepotDto depotDto) {
        Depot d = new Depot();
        d.setActeur(acteurServ.getUserConnected());
        d.setCode(depotDto.getCode());
        d.setLibelle(depotDto.getLibelle());
        d.setVille(depotDto.getVille());
        return depotRepo.save(d);
    }
}

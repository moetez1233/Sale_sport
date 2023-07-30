package com.app.FirstApp.services.depot;

import com.app.FirstApp.domain.depot.Depot;
import com.app.FirstApp.repository.depo.DepotRepo;
import com.app.FirstApp.services.Acteur.ActeurServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}

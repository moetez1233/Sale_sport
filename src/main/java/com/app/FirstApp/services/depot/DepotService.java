package com.app.FirstApp.services.depot;

import com.app.FirstApp.domain.depot.Depot;

import java.util.Optional;
import java.util.Set;

public interface DepotService {
    Optional<Depot> saveDepot(Depot depot);
    Set<Depot> getallDepot();
}

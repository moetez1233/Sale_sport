package com.app.FirstApp.services.depot;

import com.app.FirstApp.domain.depot.Depot;

import java.util.Optional;

public interface DepotService {
    Optional<Depot> saveDepot(Depot depot);
}

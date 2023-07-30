package com.app.FirstApp.repository.depo;

import com.app.FirstApp.domain.depot.Depot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepotRepo extends JpaRepository<Depot,Long> {
}

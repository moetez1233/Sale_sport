package com.app.FirstApp.repository.tier;

import com.app.FirstApp.domain.tier.Tier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TierRepo extends JpaRepository<Tier,Long> {
}

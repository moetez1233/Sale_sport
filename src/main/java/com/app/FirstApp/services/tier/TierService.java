package com.app.FirstApp.services.tier;

import com.app.FirstApp.domain.tier.Tier;

import java.util.Optional;

public interface TierService {
    Optional<Tier> saveTier(Tier tier);
}

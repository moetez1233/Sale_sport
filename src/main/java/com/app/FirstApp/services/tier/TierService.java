package com.app.FirstApp.services.tier;

import com.app.FirstApp.domain.tier.Tier;
import com.app.FirstApp.modele.tier.TierDto;

import java.util.Optional;
import java.util.Set;

public interface TierService {
    Optional<Tier> saveTier(Tier tier);
    Optional<Tier> saveTierDto(TierDto tier);
    Set<Tier> listTier();
}

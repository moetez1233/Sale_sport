package com.app.FirstApp.services.tier;

import com.app.FirstApp.domain.tier.Tier;
import com.app.FirstApp.repository.tier.TierRepo;
import com.app.FirstApp.services.Acteur.ActeurServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class TierServiceImpl implements TierService {
    @Autowired
    private ActeurServ acteurServ;
    @Autowired
    private TierRepo tierRepo;

    @Override
    public Optional<Tier> saveTier(Tier tier) {
        tier.setActeur(acteurServ.getUserConnected());
        return Optional.of(tierRepo.save(tier));
    }
}

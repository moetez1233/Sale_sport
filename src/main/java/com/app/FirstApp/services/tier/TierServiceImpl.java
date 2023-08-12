package com.app.FirstApp.services.tier;

import com.app.FirstApp.domain.tier.Tier;
import com.app.FirstApp.modele.tier.TierDto;
import com.app.FirstApp.repository.tier.TierRepo;
import com.app.FirstApp.services.Acteur.ActeurServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

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

    @Override
    public Optional<Tier> saveTierDto(TierDto tierDto) {
        Tier tier = new Tier();
        tier.setNom(tierDto.getNom());
        tier.setPrenom(tierDto.getPrenom());
        tier.setNumeroTel(tierDto.getNumeroTel());
        tier.setEmail(tierDto.getEmail());
        tier.setAdress(tierDto.getAdress());
        tier.setActeur(acteurServ.getUserConnected());
        return Optional.of(tierRepo.save(tier));
    }

    @Override
    public Set<Tier> listTier() {
        return tierRepo.getAllTierByActeurId(acteurServ.getUserConnected().getId());
    }
}

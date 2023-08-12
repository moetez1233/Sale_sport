package com.app.FirstApp.controller.tier;

import com.app.FirstApp.domain.tier.Tier;
import com.app.FirstApp.modele.tier.TierDto;
import com.app.FirstApp.services.tier.TierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/tier")
public class TierController {
    @Autowired
    private TierService tierService;

    @GetMapping
    private ResponseEntity<Set<Tier>> listTier(){
        return new ResponseEntity<>(tierService.listTier(), HttpStatus.OK);
    }
    @PostMapping
    private ResponseEntity<Tier> saveTier(@RequestBody TierDto tierModel){
        return new ResponseEntity<>(this.tierService.saveTierDto(tierModel).get(),HttpStatus.CREATED);
    }
}

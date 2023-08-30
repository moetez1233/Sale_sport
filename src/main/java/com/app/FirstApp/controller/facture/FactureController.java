package com.app.FirstApp.controller.facture;

import com.app.FirstApp.domain.depot.Depot;
import com.app.FirstApp.domain.facture.DetailFacture;
import com.app.FirstApp.domain.facture.Facture;
import com.app.FirstApp.domain.facture.StatusFacture;
import com.app.FirstApp.domain.facture.StatusPaiementFacture;
import com.app.FirstApp.domain.produits.Produits;
import com.app.FirstApp.domain.tier.Tier;
import com.app.FirstApp.modele.FactureDtoTest;
import com.app.FirstApp.modele.facture.FactureDto;
import com.app.FirstApp.services.Acteur.ActeurServ;
import com.app.FirstApp.services.depot.DepotService;
import com.app.FirstApp.services.facture.DetailFactureServ;
import com.app.FirstApp.services.facture.FactureService;
import com.app.FirstApp.services.produit.ProduitService;
import com.app.FirstApp.services.tier.TierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/facture")
public class FactureController {

   private DepotService depotService;
    private ProduitService produitService;
    private TierService tierService;
    private FactureService factureService;
    private DetailFactureServ detailFactureServ;
    private ActeurServ acteurServ;

    public FactureController(DepotService depotService, ProduitService produitService, TierService tierService, FactureService factureService,DetailFactureServ detailFactureServ, ActeurServ acteurServ) {
        this.depotService = depotService;
        this.produitService = produitService;
        this.tierService = tierService;
        this.factureService = factureService;
        this.detailFactureServ = detailFactureServ;
        this.acteurServ=acteurServ;
    }
    @PostMapping
    private ResponseEntity<FactureDto> saveFacture(@RequestBody FactureDto factureModele){
        return new ResponseEntity<>(factureService.saveFactureDto(factureModele),HttpStatus.CREATED);
    }

    @DeleteMapping("/{factureId}")
    private ResponseEntity<String> deleteFacture(@PathVariable Long factureId){
      factureService.deletFacture(factureId);
      return new ResponseEntity<>("delete_success",HttpStatus.OK);
    }
    @GetMapping
    private ResponseEntity<List<FactureDto>> listFacture(){
        return new ResponseEntity<>(factureService.getListFactureDto(),HttpStatus.OK);
    }


}

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

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
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
    @PutMapping
    private ResponseEntity<FactureDto> updateFacture(@RequestBody FactureDto factureModele){
        return new ResponseEntity<>(factureService.updateFactureDto(factureModele),HttpStatus.CREATED);
    }

    @DeleteMapping("/{factureId}")
    private ResponseEntity<String> deleteFacture(@PathVariable Long factureId){
      factureService.deletFacture(factureId);
      return new ResponseEntity<>("delete_success",HttpStatus.OK);
    }
    @GetMapping("/generateFacture/{factureId}")
    public void generateFactureToPdfFile(HttpServletResponse response,@PathVariable Long factureId) throws Exception {
        ByteArrayInputStream byteArrayInputStream=factureService.exportFactureEmploy(factureId);
        factureService.writePdfStreamToHttpServletResponse(response, byteArrayInputStream); // pdf file inn web page
    }
    @GetMapping
    private ResponseEntity<List<FactureDto>> listFacture(){
        return new ResponseEntity<>(factureService.getListFactureDto(),HttpStatus.OK);
    }

    /*@GetMapping("/addTestFacture")
    public ResponseEntity<String> getFacureSaved(){
        Depot depot = new Depot();
        depot.setCode("depot1");
        depot.setLibelle("depot1");
        depot.setVille("Tunis");
        Depot depotSaved = depotService.saveDepot(depot).get();

        Produits produits = new Produits();
        produits.setCode("iphone");
        produits.setLibell("iphone");
        produits.setPrixVente(new BigDecimal(100));
        produits.setQuantiteInitiale(new BigDecimal(45));
        produits.setQuantite(new BigDecimal(45));
        produits.setDepot(depotSaved);
        Produits produitSaved = produitService.saveProduit(produits).get();

        Tier tier = new Tier();
        tier.setNom("moetez");
        tier.setPrenom("Maddouri");
        tier.setNumeroTel(new BigDecimal(55611346));
        tier.setEmail("moetezmaddouri@gmail.com");
        tier.setAdress("Sfax-Hay-Ons");

        Tier tierSaved = tierService.saveTier(tier).get();

        Facture facture =new Facture();
        facture.setNumero("1");
        facture.setStatusFacture(StatusFacture.VALID);
        facture.setStatusPaiementFacture(StatusPaiementFacture.PAIE);
        facture.setPrixTotale(new BigDecimal(1500));
        facture.setClient(tierSaved);
        facture.setDateFacture(LocalDate.now());


        List<DetailFacture> detailFactureSet =new ArrayList<>();
        DetailFacture detailFacture = new DetailFacture();
        detailFacture.setProduits(produitSaved);
        detailFacture.setQuantite(new BigDecimal(20));
        detailFacture.setPrix(new BigDecimal(100));
        detailFactureSet.add(detailFacture);

        Set<Produits> produitSet =produitService.getAllByActeur().get();

        List<Produits> produitToUpdate = new ArrayList<>();
        Facture factureSaved = factureService.saveFacture(facture).get();
        detailFactureSet.stream().forEach(d -> {
            d.setLibelleProduit(d.getProduits().getLibell());
            produitToUpdate.add(produitSet.stream().filter(p -> p.equals(d.getProduits())).findFirst().get());
            d.setFacture(factureSaved);
                }
        );
        List<DetailFacture> listDetailsSaved = detailFactureServ.saveDetailleFacture(detailFactureSet);
        produitService.saveAllProduit(produitToUpdate);





        return new ResponseEntity<>("success add ", HttpStatus.CREATED);
    }
    */

    @GetMapping("/testFacture")
    public ResponseEntity<FactureDtoTest> testFacture(){
        FactureDtoTest factureDto = new FactureDtoTest();
        factureDto.setFacture(factureService.getAllFactures().get(0));
        factureDto.setDetailFactures(detailFactureServ.getSetDetailFactures(factureDto.getFacture().getId()));

        return new ResponseEntity<>(factureDto,HttpStatus.OK);
    }
}

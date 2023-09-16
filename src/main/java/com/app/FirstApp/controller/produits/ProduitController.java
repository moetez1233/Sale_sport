package com.app.FirstApp.controller.produits;

import com.app.FirstApp.domain.produits.Produits;
import com.app.FirstApp.services.produit.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/produit")
public class ProduitController {
    @Autowired
    private ProduitService produitService;

    @GetMapping
    private ResponseEntity<Set<Produits>> listProduits(){
        return new ResponseEntity<>(produitService.getAllByActeur().get(), HttpStatus.OK);
    }
    @PostMapping
    private ResponseEntity<Produits> saveProduit(@RequestBody Produits produitModel){
        return new ResponseEntity<>(produitService.saveProduit(produitModel).get(),HttpStatus.CREATED);
    }

    @DeleteMapping("/{prodId}")
    private ResponseEntity<String> deleteFacture(@PathVariable Long prodId){
        produitService.deleteProduit(prodId);
        return new ResponseEntity<>("delete_success",HttpStatus.OK);
    }
}

package com.app.FirstApp.controller.depot;

import com.app.FirstApp.domain.depot.Depot;
import com.app.FirstApp.modele.depot.DepotDto;
import com.app.FirstApp.services.depot.DepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
@RestController
@RequestMapping("/api/depot")
public class DepotController {

    @Autowired
    private DepotService depotService;

    @GetMapping
    private ResponseEntity<Set<Depot>> getAll(){
        return new ResponseEntity<>(depotService.getallDepot(), HttpStatus.OK);
    }
    @PostMapping
    private ResponseEntity<Depot> saveDepot(@RequestBody DepotDto depot){
        return new ResponseEntity<>(depotService.saveDepot(depot),HttpStatus.CREATED);
    }
}

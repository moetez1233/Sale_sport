package com.app.FirstApp.controller.depot;

import com.app.FirstApp.domain.depot.Depot;
import com.app.FirstApp.services.depot.DepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/depot")
public class DepotController {

    @Autowired
    private DepotService depotService;

    @GetMapping
    private ResponseEntity<Set<Depot>> getAll(    @RequestHeader(value="Accept") String acceptHeader,
                                                  @RequestHeader(value="Authorization") String authorizationHeader){
        return new ResponseEntity<>(depotService.getallDepot(), HttpStatus.OK);
    }
}

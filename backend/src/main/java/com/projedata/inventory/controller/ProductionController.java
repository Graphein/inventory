package com.projedata.inventory.controller;

import com.projedata.inventory.entity.Production;
import com.projedata.inventory.service.ProductionService;
import com.projedata.inventory.dto.ProductionSuggestionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productions")
@CrossOrigin("*")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping
    public ResponseEntity<List<Production>> findAll() {
        return ResponseEntity.ok(productionService.findAll());
    }

    @PostMapping
    public ResponseEntity<Production> create(@RequestBody Production production) {
        return ResponseEntity.ok(productionService.createProduction(production));
    }
    @GetMapping("/suggestions")
    public ResponseEntity<List<ProductionSuggestionDTO>> getSuggestions() {
        return ResponseEntity.ok(productionService.getSuggestions());
    }
}
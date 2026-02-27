package com.projedata.inventory.controller;

import com.projedata.inventory.entity.RawMaterial;
import com.projedata.inventory.service.RawMaterialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/raw-materials")
@CrossOrigin("*")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    public RawMaterialController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @GetMapping
    public ResponseEntity<List<RawMaterial>> findAll() {
        return ResponseEntity.ok(rawMaterialService.findAll());
    }

    @PostMapping
    public ResponseEntity<RawMaterial> create(@RequestBody RawMaterial rawMaterial) {
        return ResponseEntity.ok(rawMaterialService.save(rawMaterial));
    }
}
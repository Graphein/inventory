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

    private final RawMaterialService service;

    public RawMaterialController(RawMaterialService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RawMaterial>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<RawMaterial> create(@RequestBody RawMaterial rawMaterial) {
        return ResponseEntity.ok(service.create(rawMaterial));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RawMaterial> update(
            @PathVariable Long id,
            @RequestBody RawMaterial rawMaterial) {

        return ResponseEntity.ok(service.update(id, rawMaterial));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
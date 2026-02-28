package com.projedata.inventory.controller;

import com.projedata.inventory.dto.ProductMaterialDTO;
import com.projedata.inventory.service.ProductMaterialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-materials")
@CrossOrigin("*")
public class ProductMaterialController {

    private final ProductMaterialService service;

    public ProductMaterialController(ProductMaterialService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductMaterialDTO> create(
            @RequestBody ProductMaterialDTO dto) {

        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProductMaterialDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductMaterialDTO>> findByProduct(
            @PathVariable Long productId) {

        return ResponseEntity.ok(service.findByProduct(productId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
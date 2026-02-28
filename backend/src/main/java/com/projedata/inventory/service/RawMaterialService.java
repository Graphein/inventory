package com.projedata.inventory.service;

import com.projedata.inventory.entity.RawMaterial;
import com.projedata.inventory.repository.RawMaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawMaterialService {

    private final RawMaterialRepository repository;

    public RawMaterialService(RawMaterialRepository repository) {
        this.repository = repository;
    }

    public List<RawMaterial> findAll() {
        return repository.findAll();
    }

    public RawMaterial findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("RawMaterial not found"));
    }

    public RawMaterial create(RawMaterial rawMaterial) {
        return repository.save(rawMaterial);
    }

    public RawMaterial update(Long id, RawMaterial updated) {
        RawMaterial existing = findById(id);

        existing.setName(updated.getName());
        existing.setStockQuantity(updated.getStockQuantity());

        return repository.save(existing);
    }

    public void delete(Long id) {
        RawMaterial existing = findById(id);
        repository.delete(existing);
    }
}
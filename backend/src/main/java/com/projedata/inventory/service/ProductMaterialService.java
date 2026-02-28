package com.projedata.inventory.service;

import com.projedata.inventory.dto.ProductMaterialDTO;
import com.projedata.inventory.entity.Product;
import com.projedata.inventory.entity.ProductMaterial;
import com.projedata.inventory.entity.RawMaterial;
import com.projedata.inventory.repository.ProductMaterialRepository;
import com.projedata.inventory.repository.ProductRepository;
import com.projedata.inventory.repository.RawMaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductMaterialService {

    private final ProductMaterialRepository repository;
    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public ProductMaterialService(
            ProductMaterialRepository repository,
            ProductRepository productRepository,
            RawMaterialRepository rawMaterialRepository) {

        this.repository = repository;
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public ProductMaterialDTO create(ProductMaterialDTO dto) {

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        RawMaterial rawMaterial = rawMaterialRepository.findById(dto.getRawMaterialId())
                .orElseThrow(() -> new RuntimeException("Raw material not found"));

        ProductMaterial entity = ProductMaterial.builder()
                .product(product)
                .rawMaterial(rawMaterial)
                .quantity(dto.getQuantity())
                .build();

        ProductMaterial saved = repository.save(entity);

        return ProductMaterialDTO.builder()
                .id(saved.getId())
                .productId(saved.getProduct().getId())
                .rawMaterialId(saved.getRawMaterial().getId())
                .quantity(saved.getQuantity())
                .build();
    }

    public List<ProductMaterialDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductMaterialDTO> findByProduct(Long productId) {
        return repository.findByProductId(productId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private ProductMaterialDTO mapToDTO(ProductMaterial entity) {
        return ProductMaterialDTO.builder()
                .id(entity.getId())
                .productId(entity.getProduct().getId())
                .rawMaterialId(entity.getRawMaterial().getId())
                .quantity(entity.getQuantity())
                .build();
    }
}
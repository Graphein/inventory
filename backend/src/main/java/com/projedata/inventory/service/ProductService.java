package com.projedata.inventory.service;

import com.projedata.inventory.dto.ProductMaterialDTO;
import com.projedata.inventory.dto.ProductWithMaterialsDTO;
import com.projedata.inventory.entity.Product;
import com.projedata.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    /**
     * Retorna todos os produtos ordenados por preço DESC,
     * convertendo os materiais para ProductMaterialDTO para evitar referência circular
     */
    public List<ProductWithMaterialsDTO> findAllOrderedByPriority() {
    return repository.findAllByOrderByPriceDesc()
            .stream()
            .map(product -> {
                // Converte materiais para DTO sem referência circular
                List<ProductMaterialDTO> materials = product.getMaterials()
                        .stream()
                        .map(m -> ProductMaterialDTO.builder()
                                .id(m.getId())
                                .productId(m.getProduct().getId())  // <--- AQUI
                                .rawMaterialId(m.getRawMaterial().getId())
                                .quantity(m.getQuantity())
                                .build())
                        .collect(Collectors.toList());

                // Constrói DTO do produto com materiais
                return ProductWithMaterialsDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .quantity(product.getQuantity() != null ? product.getQuantity() : 0) // opcional para evitar null
                        .materials(materials)
                        .build();
            })
            .collect(Collectors.toList());
    }

    // Métodos padrões de CRUD
    public Product create(Product product) {
        return repository.save(product);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public Product update(Long id, Product product) {
        Product existing = findById(id);
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setQuantity(product.getQuantity());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
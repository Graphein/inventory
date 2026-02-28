package com.projedata.inventory.service;

import com.projedata.inventory.dto.ProductionSuggestionDTO;
import com.projedata.inventory.entity.Product;
import com.projedata.inventory.entity.ProductMaterial;
import com.projedata.inventory.entity.Production;
import com.projedata.inventory.repository.ProductRepository;
import com.projedata.inventory.repository.ProductionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductionService {

    private final ProductionRepository productionRepository;
    private final ProductRepository productRepository;

    public ProductionService(ProductionRepository productionRepository,
                             ProductRepository productRepository) {
        this.productionRepository = productionRepository;
        this.productRepository = productRepository;
    }

    public List<Production> findAll() {
        return productionRepository.findAll();
    }

    public Production createProduction(Production production) {
        return productionRepository.save(production);
    }
    @Transactional
    public List<ProductionSuggestionDTO> getSuggestions() {

    List<Product> products = productRepository.findAll();

    return products.stream()
            .sorted(Comparator.comparing(Product::getPrice).reversed())
            .map(product -> {

                if (product.getMaterials() == null || product.getMaterials().isEmpty()) {
                    return null;
                }

                int maxProduction = Integer.MAX_VALUE;

                for (ProductMaterial pm : product.getMaterials()) {

                    int stock = pm.getRawMaterial().getStockQuantity();
                    double required = pm.getQuantity();

                    if (required <= 0) continue;

                    int possible = (int) Math.floor(stock / required);

                    maxProduction = Math.min(maxProduction, possible);
                }

                if (maxProduction == Integer.MAX_VALUE) {
                    maxProduction = 0;
                }

                return ProductionSuggestionDTO.builder()
                        .productId(product.getId())
                        .productName(product.getName())
                        .quantityPossible(maxProduction)
                        .totalValue(maxProduction * product.getPrice())
                        .build();
            })
            .filter(dto -> dto != null && dto.getQuantityPossible() > 0)
            .toList();
    }
}
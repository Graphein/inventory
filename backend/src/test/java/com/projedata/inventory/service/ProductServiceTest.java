package com.projedata.inventory.service;

import com.projedata.inventory.dto.ProductionSuggestionDTO;
import com.projedata.inventory.entity.Product;
import com.projedata.inventory.entity.ProductMaterial;
import com.projedata.inventory.entity.RawMaterial;
import com.projedata.inventory.repository.ProductRepository;
import com.projedata.inventory.repository.ProductionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProductionServiceTest {

    @Mock
    private ProductionRepository productionRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductionService productionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSuggestions() {
        // Monta um produto com materiais
        RawMaterial mat1 = new RawMaterial();
        mat1.setId(1L);
        mat1.setStockQuantity(100);

        ProductMaterial pm = new ProductMaterial();
        pm.setQuantity(10);
        pm.setRawMaterial(mat1);

        Product product = new Product();
        product.setId(1L);
        product.setName("Chair");
        product.setPrice(50.0);
        product.setMaterials(List.of(pm));

        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductionSuggestionDTO> suggestions = productionService.getSuggestions();

        assertThat(suggestions).hasSize(1);
        ProductionSuggestionDTO dto = suggestions.get(0);
        assertThat(dto.getProductName()).isEqualTo("Chair");
        assertThat(dto.getQuantityPossible()).isEqualTo(10); // 100 stock / 10 required
        assertThat(dto.getTotalValue()).isEqualTo(500.0);

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetSuggestionsWithNoMaterials() {
        Product product = new Product();
        product.setId(2L);
        product.setName("Desk");
        product.setPrice(200.0);
        product.setMaterials(List.of());

        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductionSuggestionDTO> suggestions = productionService.getSuggestions();

        assertThat(suggestions).isEmpty();
        verify(productRepository, times(1)).findAll();
    }
}
package com.projedata.inventory.service;

import com.projedata.inventory.dto.ProductionSuggestionDTO;
import com.projedata.inventory.entity.Production;
import com.projedata.inventory.entity.Product;
import com.projedata.inventory.entity.ProductMaterial;
import com.projedata.inventory.entity.RawMaterial;
import com.projedata.inventory.repository.ProductionRepository;
import com.projedata.inventory.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProductionsServiceTest {

    @Mock
    private ProductionRepository productionRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductionService productionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Production p = new Production();
        when(productionRepository.findAll()).thenReturn(List.of(p));

        List<Production> productions = productionService.findAll();
        assertThat(productions).hasSize(1);
        verify(productionRepository).findAll();
    }

    @Test
    void testCreateProduction() {
        Production p = new Production();
        when(productionRepository.save(p)).thenReturn(p);

        Production saved = productionService.createProduction(p);
        assertThat(saved).isEqualTo(p);
        verify(productionRepository).save(p);
    }

    @Test
    void testGetSuggestions() {
        RawMaterial rm = RawMaterial.builder().stockQuantity(50).build();
        ProductMaterial pm = new ProductMaterial();
        pm.setQuantity(10);
        pm.setRawMaterial(rm);

        Product product = Product.builder()
                .id(1L)
                .name("Chair")
                .price(100.0)
                .materials(List.of(pm))
                .build();

        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductionSuggestionDTO> suggestions = productionService.getSuggestions();
        assertThat(suggestions).hasSize(1);
        ProductionSuggestionDTO dto = suggestions.get(0);
        assertThat(dto.getQuantityPossible()).isEqualTo(5); // 50 / 10
        assertThat(dto.getTotalValue()).isEqualTo(500.0);
    }
}
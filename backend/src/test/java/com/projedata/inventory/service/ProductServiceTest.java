package com.projedata.inventory.service;

import com.projedata.inventory.entity.Product;
import com.projedata.inventory.entity.Production;
import com.projedata.inventory.repository.ProductionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductionServiceTest {

    @Mock
    private ProductionRepository repository;

    @InjectMocks
    private ProductionService service;

    private Production production;

    @BeforeEach
    void setup() {
        Product product = Product.builder()
                .id(1L)
                .name("Produto Teste")
                .price(100.0)
                .quantity(10)
                .build();

        production = Production.builder()
                .id(1L)
                .quantityProduced(5)
                .productionDate(LocalDateTime.now())
                .product(product)
                .build();
    }

    @Test
    void shouldCreateProduction() {
        when(repository.save(any(Production.class))).thenReturn(production);

        Production saved = service.createProduction(production);

        assertNotNull(saved);
        assertEquals(5, saved.getQuantityProduced());
        verify(repository, times(1)).save(production);
    }

    @Test
    void shouldReturnAllProductions() {
        when(repository.findAll()).thenReturn(List.of(production));

        List<Production> list = service.findAll();

        assertEquals(1, list.size());
        verify(repository, times(1)).findAll();
    }
}
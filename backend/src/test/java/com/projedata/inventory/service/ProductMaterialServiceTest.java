package com.projedata.inventory.service;

import com.projedata.inventory.dto.ProductMaterialDTO;
import com.projedata.inventory.entity.Product;
import com.projedata.inventory.entity.ProductMaterial;
import com.projedata.inventory.entity.RawMaterial;
import com.projedata.inventory.repository.ProductMaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProductMaterialServiceTest {

    @Mock
    private ProductMaterialRepository repository;

    @InjectMocks
    private ProductMaterialService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        ProductMaterialDTO dto = new ProductMaterialDTO();
        dto.setQuantity(10);

        ProductMaterial pm = new ProductMaterial();
        when(repository.save(any(ProductMaterial.class))).thenReturn(pm);

        ProductMaterialDTO saved = service.create(dto);
        assertThat(saved).isNotNull();
        verify(repository).save(any(ProductMaterial.class));
    }

    @Test
    void testFindAll() {
        ProductMaterial pm = new ProductMaterial();
        when(repository.findAll()).thenReturn(List.of(pm));

        List<ProductMaterialDTO> list = service.findAll();
        assertThat(list).hasSize(1);
    }

    @Test
    void testFindByProduct() {
        Product p = new Product();
        p.setId(1L);

        ProductMaterial pm = new ProductMaterial();
        pm.setProduct(p);

        when(repository.findByProductId(1L)).thenReturn(List.of(pm));

        List<ProductMaterialDTO> list = service.findByProduct(1L);
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getProductId()).isEqualTo(1L);
    }

    @Test
    void testDelete() {
        ProductMaterial pm = new ProductMaterial();
        when(repository.findById(1L)).thenReturn(Optional.of(pm));

        service.delete(1L);
        verify(repository).delete(pm);
    }
}
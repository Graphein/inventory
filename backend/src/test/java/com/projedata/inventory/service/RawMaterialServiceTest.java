package com.projedata.inventory.service;

import com.projedata.inventory.entity.RawMaterial;
import com.projedata.inventory.repository.RawMaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RawMaterialServiceTest {

    @Mock
    private RawMaterialRepository repository;

    @InjectMocks
    private RawMaterialService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        RawMaterial r = new RawMaterial();
        when(repository.findAll()).thenReturn(List.of(r));

        List<RawMaterial> result = service.findAll();
        assertThat(result).hasSize(1);
    }

    @Test
    void testFindById() {
        RawMaterial r = new RawMaterial();
        when(repository.findById(1L)).thenReturn(Optional.of(r));

        RawMaterial found = service.findById(1L);
        assertThat(found).isEqualTo(r);
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.findById(1L));
    }

    @Test
    void testCreateAndUpdate() {
        RawMaterial r = new RawMaterial();
        r.setName("Steel");
        r.setStockQuantity(100);

        when(repository.save(r)).thenReturn(r);

        RawMaterial saved = service.create(r);
        assertThat(saved).isEqualTo(r);

        RawMaterial updated = new RawMaterial();
        updated.setName("Iron");
        updated.setStockQuantity(50);

        when(repository.findById(1L)).thenReturn(Optional.of(r));
        when(repository.save(r)).thenReturn(r);

        RawMaterial result = service.update(1L, updated);
        assertThat(result.getName()).isEqualTo("Iron");
        assertThat(result.getStockQuantity()).isEqualTo(50);
    }

    @Test
    void testDelete() {
        RawMaterial r = new RawMaterial();
        when(repository.findById(1L)).thenReturn(Optional.of(r));

        service.delete(1L);
        verify(repository).delete(r);
    }
}
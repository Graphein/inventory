package com.projedata.inventory.repository;

import com.projedata.inventory.entity.RawMaterial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RawMaterialRepositoryTest {

    @Autowired
    private RawMaterialRepository repository;

    @Test
    void testCreateAndFindRawMaterial() {
        RawMaterial raw = RawMaterial.builder()
                .name("Steel")
                .stockQuantity(100)
                .build();

        RawMaterial saved = repository.save(raw);
        Optional<RawMaterial> found = repository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Steel");
    }

    @Test
    void testDeleteRawMaterial() {
        RawMaterial raw = RawMaterial.builder()
                .name("Copper")
                .stockQuantity(50)
                .build();

        RawMaterial saved = repository.save(raw);
        repository.delete(saved);

        Optional<RawMaterial> found = repository.findById(saved.getId());
        assertThat(found).isEmpty();
    }
}
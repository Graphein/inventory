package com.projedata.inventory.repository;

import com.projedata.inventory.entity.Production;
import com.projedata.inventory.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductionRepositoryTest {

    @Autowired
    private ProductionRepository repository;

    @Test
    void testCreateAndFindProduction() {
        Product product = new Product();
        product.setName("Chair");
        product.setPrice(120.0);
        product.setQuantity(10);

        Production production = new Production();
        production.setProduct(product);
        production.setQuantity(5);

        Production saved = repository.save(production);
        Optional<Production> found = repository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getQuantity()).isEqualTo(5);
    }

    @Test
    void testDeleteProduction() {
        Product product = new Product();
        product.setName("Desk");
        product.setPrice(200.0);
        product.setQuantity(5);

        Production production = new Production();
        production.setProduct(product);
        production.setQuantity(2);

        Production saved = repository.save(production);
        repository.delete(saved);

        Optional<Production> found = repository.findById(saved.getId());
        assertThat(found).isEmpty();
    }
}
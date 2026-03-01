package com.projedata.inventory.repository;

import com.projedata.inventory.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testCreateAndFindProduct() {
        Product p = new Product();
        p.setName("Chair");
        p.setPrice(120.0);
        p.setQuantity(10);

        Product saved = productRepository.save(p);
        Optional<Product> found = productRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Chair");
    }

    @Test
    void testDeleteProduct() {
        Product p = new Product();
        p.setName("Desk");
        p.setPrice(200.0);
        p.setQuantity(5);

        Product saved = productRepository.save(p);
        productRepository.delete(saved);

        Optional<Product> found = productRepository.findById(saved.getId());
        assertThat(found).isEmpty();
    }
}
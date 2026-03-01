package com.projedata.inventory.repository;

import com.projedata.inventory.entity.Product;
import com.projedata.inventory.entity.ProductMaterial;
import com.projedata.inventory.entity.RawMaterial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductMaterialRepositoryTest {

    @Autowired
    private ProductMaterialRepository repository;

    @Test
    void testCreateAndFindByProductId() {
        Product product = new Product();
        product.setName("Table");
        product.setPrice(150.0);
        product.setQuantity(5);

        RawMaterial raw = new RawMaterial();
        raw.setName("Wood");
        raw.setStockQuantity(100);

        ProductMaterial pm = new ProductMaterial();
        pm.setProduct(product);
        pm.setRawMaterial(raw);
        pm.setQuantity(10);

        ProductMaterial saved = repository.save(pm);
        List<ProductMaterial> materials = repository.findByProductId(product.getId());

        assertThat(materials).isNotEmpty();
        assertThat(materials.get(0).getQuantity()).isEqualTo(10);
    }

    @Test
    void testDeleteProductMaterial() {
        Product product = new Product();
        product.setName("Shelf");
        product.setPrice(200.0);
        product.setQuantity(3);

        RawMaterial raw = new RawMaterial();
        raw.setName("Metal");
        raw.setStockQuantity(50);

        ProductMaterial pm = new ProductMaterial();
        pm.setProduct(product);
        pm.setRawMaterial(raw);
        pm.setQuantity(5);

        ProductMaterial saved = repository.save(pm);
        repository.delete(saved);

        List<ProductMaterial> materials = repository.findByProductId(product.getId());
        assertThat(materials).isEmpty();
    }
}
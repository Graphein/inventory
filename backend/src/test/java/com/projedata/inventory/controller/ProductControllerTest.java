package com.projedata.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projedata.inventory.entity.Product;
import com.projedata.inventory.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Chair");
        product.setPrice(120.0);
        product.setQuantity(10);
    }

    @Test
    void testFindAllProducts() throws Exception {
        Mockito.when(productService.findAll()).thenReturn(List.of(product));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Chair"))
                .andExpect(jsonPath("$[0].price").value(120.0));
    }

    @Test
    void testFindProductById() throws Exception {
        Mockito.when(productService.findById(anyLong())).thenReturn(product);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Chair"))
                .andExpect(jsonPath("$.price").value(120.0));
    }

    @Test
    void testCreateProduct() throws Exception {
        Mockito.when(productService.save(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Chair"))
                .andExpect(jsonPath("$.price").value(120.0));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product updated = new Product();
        updated.setId(1L);
        updated.setName("Updated Chair");
        updated.setPrice(150.0);
        updated.setQuantity(5);

        Mockito.when(productService.update(anyLong(), any(Product.class))).thenReturn(updated);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Chair"))
                .andExpect(jsonPath("$.price").value(150.0));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Mockito.doNothing().when(productService).delete(anyLong());

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }
}
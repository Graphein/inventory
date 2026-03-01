package com.projedata.inventory.controller;

import com.projedata.inventory.dto.ProductMaterialDTO;
import com.projedata.inventory.service.ProductMaterialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductMaterialController.class)
public class ProductMaterialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductMaterialService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testFindAll() throws Exception {
        List<ProductMaterialDTO> list = Arrays.asList(
                new ProductMaterialDTO(1L, 1L, 2.0),
                new ProductMaterialDTO(2L, 1L, 3.0)
        );

        Mockito.when(service.findAll()).thenReturn(list);

        mockMvc.perform(get("/api/product-materials"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void testFindByProduct() throws Exception {
        List<ProductMaterialDTO> list = Arrays.asList(
                new ProductMaterialDTO(1L, 1L, 2.0)
        );

        Mockito.when(service.findByProduct(1L)).thenReturn(list);

        mockMvc.perform(get("/api/product-materials/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].productId").value(1));
    }

    @Test
    void testCreate() throws Exception {
        ProductMaterialDTO dto = new ProductMaterialDTO(null, 1L, 5.0);
        ProductMaterialDTO saved = new ProductMaterialDTO(1L, 1L, 5.0);

        Mockito.when(service.create(any(ProductMaterialDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/api/product-materials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.quantity").value(5.0));
    }

    @Test
    void testDelete() throws Exception {
        Mockito.doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/product-materials/1"))
                .andExpect(status().isNoContent());
    }
}
package com.projedata.inventory.controller;

import com.projedata.inventory.dto.ProductionSuggestionDTO;
import com.projedata.inventory.entity.Production;
import com.projedata.inventory.service.ProductionService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductionController.class)
public class ProductionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductionService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testFindAll() throws Exception {
        List<Production> productions = Arrays.asList(
                new Production(1L, "Production 1"),
                new Production(2L, "Production 2")
        );

        Mockito.when(service.findAll()).thenReturn(productions);

        mockMvc.perform(get("/api/productions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void testCreate() throws Exception {
        Production newProduction = new Production(null, "Production X");
        Production saved = new Production(1L, "Production X");

        Mockito.when(service.createProduction(any(Production.class))).thenReturn(saved);

        mockMvc.perform(post("/api/productions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Production X"));
    }

    @Test
    void testGetSuggestions() throws Exception {
        ProductionSuggestionDTO dto = ProductionSuggestionDTO.builder()
                .productId(1L)
                .productName("Product A")
                .quantityPossible(10)
                .totalValue(1000.0)
                .build();

        Mockito.when(service.getSuggestions()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/productions/suggestions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].productName").value("Product A"));
    }
}
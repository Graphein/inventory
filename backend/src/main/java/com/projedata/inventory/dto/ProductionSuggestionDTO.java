package com.projedata.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionSuggestionDTO {

    private Long productId;
    private String productName;
    private Integer quantityPossible;
    private Double totalValue;
}
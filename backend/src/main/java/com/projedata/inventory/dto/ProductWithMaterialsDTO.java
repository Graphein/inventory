package com.projedata.inventory.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductWithMaterialsDTO {

    private Long id;
    private String name;
    private Double price;
    private Integer quantity;

    private List<ProductMaterialDTO> materials; 
}
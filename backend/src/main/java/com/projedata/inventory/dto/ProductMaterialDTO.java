package com.projedata.inventory.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductMaterialDTO {

    private Long id;
    private Long productId;
    private Long rawMaterialId;
    private Double quantity;
}
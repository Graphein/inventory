package com.projedata.inventory.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RawMaterialDTO {

    private Long id;
    private String name;
    private Integer stockQuantity;
}
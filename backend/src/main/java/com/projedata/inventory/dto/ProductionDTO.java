package com.projedata.inventory.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionDTO {

    private Long id;
    private Integer quantityProduced;
    private LocalDateTime productionDate;
    private Long productId;
}
package com.projedata.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "productions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Production {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantityProduced;

    private LocalDateTime productionDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
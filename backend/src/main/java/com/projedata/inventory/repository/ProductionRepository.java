package com.projedata.inventory.repository;

import com.projedata.inventory.entity.Production;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionRepository extends JpaRepository<Production, Long> {
}
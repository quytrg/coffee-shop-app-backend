package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.StockBatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockBatchRepository extends JpaRepository<StockBatch, Long> {
}

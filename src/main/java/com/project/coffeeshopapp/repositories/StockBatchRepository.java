package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.customrepositories.StockBatchRepositoryCustom;
import com.project.coffeeshopapp.models.StockBatch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StockBatchRepository extends JpaRepository<StockBatch, Long>, JpaSpecificationExecutor<StockBatch>, StockBatchRepositoryCustom {
    @EntityGraph(attributePaths = {
            "ingredient",
            "supplyOrderItem",
            "supplyOrderItem.supplyOrder"
    }, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT sb FROM StockBatch sb WHERE sb.id = :id")
    Optional<StockBatch> findByIdWithIngredientAndSupplyOrderItem(Long id);
}

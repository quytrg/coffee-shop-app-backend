package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.customrepositories.StockBatchRepositoryCustom;
import com.project.coffeeshopapp.models.StockBatch;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StockBatchRepository extends JpaRepository<StockBatch, Long>, JpaSpecificationExecutor<StockBatch>, StockBatchRepositoryCustom {
    @EntityGraph(attributePaths = {
            "ingredient",
            "supplyOrderItem",
            "supplyOrderItem.supplyOrder"
    }, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT sb FROM StockBatch sb WHERE sb.id = :id")
    Optional<StockBatch> findByIdWithIngredientAndSupplyOrderItem(Long id);

    @Modifying
    @Query("UPDATE StockBatch sb SET sb.deleted = true WHERE sb.id = :id")
    void softDelete(@Param("id") Long id);
}

package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.customrepositories.StockBatchRepositoryCustom;
import com.project.coffeeshopapp.models.StockBatch;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    /**
     * Finds all available StockBatches for a given ingredientId, ordered by expirationDate ascending
     * and remainingQuantity ascending.
     *
     * @param ingredientId The ID of the ingredient.
     * @return List of StockBatches.
     */
    @Query("SELECT sb FROM StockBatch sb JOIN sb.supplyOrderItem soi " +
            "WHERE sb.ingredient.id = :ingredientId AND sb.remainingQuantity > 0 " +
            "ORDER BY soi.expirationDate ASC, sb.remainingQuantity ASC")
    List<StockBatch> findAvailableStockBatchesByIngredientIdOrderByExpirationDateAscRemainingQuantityAsc(@Param("ingredientId") Long ingredientId);
}

package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long>, JpaSpecificationExecutor<ProductVariant> {
    @Query("SELECT pv FROM ProductVariant pv LEFT JOIN FETCH pv.product p LEFT JOIN FETCH p.category c " +
            " WHERE pv.id = :id AND p.id = :productId ")
    Optional<ProductVariant> findByIdAndProductId(@Param("id") Long id, @Param("productId") Long productId);
//    Page<ProductVariant> findByProductId(Long productId, Pageable pageable);

    @Modifying
    @Query("UPDATE ProductVariant pv SET pv.deleted = true WHERE pv.id = :id")
    void softDelete(@Param("id") Long id);
}

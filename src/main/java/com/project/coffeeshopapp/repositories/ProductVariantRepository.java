package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.ProductVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long>, JpaSpecificationExecutor<ProductVariant> {
    Optional<ProductVariant> findByIdAndProductId(Long id, Long productId);
    Page<ProductVariant> findByProductId(Long productId, Pageable pageable);
}
package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Modifying
    @Query("UPDATE Product p SET p.deleted = true WHERE p.id = :id")
    void softDelete(@Param("id") Long id);

    @Override
    @EntityGraph(attributePaths = {"category"})
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
}

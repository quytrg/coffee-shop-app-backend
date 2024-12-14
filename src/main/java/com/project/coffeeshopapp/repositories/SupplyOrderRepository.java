package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.Product;
import com.project.coffeeshopapp.models.SupplyOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface SupplyOrderRepository extends JpaRepository<SupplyOrder, Long>, JpaSpecificationExecutor<SupplyOrder> {
    @Modifying
    @Query("UPDATE SupplyOrder so SET so.deleted = true WHERE so.id = :id")
    void softDelete(@Param("id") Long id);

    @Override
    @EntityGraph(attributePaths = {"supplier"})
    Page<SupplyOrder> findAll(Specification<SupplyOrder> spec, Pageable pageable);
}

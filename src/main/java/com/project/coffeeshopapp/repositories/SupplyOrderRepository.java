package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.SupplyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SupplyOrderRepository extends JpaRepository<SupplyOrder, Long> {
    @Modifying
    @Query("UPDATE SupplyOrder so SET so.deleted = true WHERE so.id = :id")
    void softDelete(@Param("id") Long id);
}

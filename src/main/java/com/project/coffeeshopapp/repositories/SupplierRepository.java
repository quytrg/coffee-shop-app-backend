package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SupplierRepository extends JpaRepository<Supplier, Long>, JpaSpecificationExecutor<Supplier> {
    @Modifying
    @Query("UPDATE Supplier s SET s.deleted = true WHERE s.id = :id")
    void softDelete(@Param("id") Long id);
}

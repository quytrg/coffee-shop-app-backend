package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    @Modifying
    @Query("UPDATE Category c SET c.deleted = true WHERE c.id = :id")
    void softDelete(@Param("id") Long id);
}

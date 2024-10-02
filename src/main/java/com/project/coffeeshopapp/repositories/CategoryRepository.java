package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.Category;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    @Modifying
    @Query("UPDATE Category c SET c.deleted = true WHERE c.id = :id")
    void softDelete(@Param("id") Long id);

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.images WHERE c.id = :id")
    Optional<Category> findByIdWithImages(@Param("id") Long id);
}

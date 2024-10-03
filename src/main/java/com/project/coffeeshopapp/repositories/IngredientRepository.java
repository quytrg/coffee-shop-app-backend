package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>, JpaSpecificationExecutor<Ingredient> {
    @Modifying
    @Query("UPDATE Ingredient i SET i.deleted = true WHERE i.id = :id")
    void softDelete(@Param("id") Long id);
}

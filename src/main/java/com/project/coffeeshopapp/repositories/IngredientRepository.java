package com.project.coffeeshopapp.repositories;

import com.project.coffeeshopapp.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}

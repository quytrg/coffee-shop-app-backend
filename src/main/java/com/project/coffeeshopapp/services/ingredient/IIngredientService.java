package com.project.coffeeshopapp.services.ingredient;

import com.project.coffeeshopapp.dtos.request.ingredient.IngredientCreateRequest;
import com.project.coffeeshopapp.dtos.request.ingredient.IngredientUpdateRequest;
import com.project.coffeeshopapp.dtos.response.ingredient.IngredientResponse;

public interface IIngredientService {
    IngredientResponse createIngredient(IngredientCreateRequest ingredientCreateRequest);
    IngredientResponse updateIngredient(Long id, IngredientUpdateRequest ingredientUpdateRequest);
}

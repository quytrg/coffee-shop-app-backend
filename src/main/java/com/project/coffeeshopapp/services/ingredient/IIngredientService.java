package com.project.coffeeshopapp.services.ingredient;

import com.project.coffeeshopapp.dtos.request.ingredient.IngredientCreateRequest;
import com.project.coffeeshopapp.dtos.response.ingredient.IngredientResponse;
import com.project.coffeeshopapp.models.Ingredient;

public interface IIngredientService {
    IngredientResponse createIngredient(IngredientCreateRequest ingredientCreateRequest);
}

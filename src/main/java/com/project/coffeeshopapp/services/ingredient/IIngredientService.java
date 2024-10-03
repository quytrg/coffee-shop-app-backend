package com.project.coffeeshopapp.services.ingredient;

import com.project.coffeeshopapp.dtos.request.ingredient.IngredientCreateRequest;
import com.project.coffeeshopapp.dtos.request.ingredient.IngredientSearchRequest;
import com.project.coffeeshopapp.dtos.request.ingredient.IngredientUpdateRequest;
import com.project.coffeeshopapp.dtos.response.ingredient.IngredientResponse;
import com.project.coffeeshopapp.dtos.response.ingredient.IngredientSummaryResponse;
import org.springframework.data.domain.Page;

public interface IIngredientService {
    IngredientResponse createIngredient(IngredientCreateRequest ingredientCreateRequest);
    IngredientResponse updateIngredient(Long id, IngredientUpdateRequest ingredientUpdateRequest);
    Page<IngredientSummaryResponse> getIngredients(IngredientSearchRequest ingredientSearchRequest);
}

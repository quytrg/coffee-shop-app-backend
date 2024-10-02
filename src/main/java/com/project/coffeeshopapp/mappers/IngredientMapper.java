package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.request.ingredient.IngredientCreateRequest;
import com.project.coffeeshopapp.dtos.response.ingredient.IngredientResponse;
import com.project.coffeeshopapp.models.Ingredient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    Ingredient ingredientCreateRequestToIngredient(IngredientCreateRequest ingredientCreateRequest);
    IngredientResponse ingredientToIngredientResponse(Ingredient ingredient);
}

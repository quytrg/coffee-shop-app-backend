package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.request.ingredient.IngredientCreateRequest;
import com.project.coffeeshopapp.dtos.request.ingredient.IngredientUpdateRequest;
import com.project.coffeeshopapp.dtos.response.ingredient.IngredientResponse;
import com.project.coffeeshopapp.dtos.response.ingredient.IngredientSummaryResponse;
import com.project.coffeeshopapp.models.Ingredient;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    Ingredient ingredientCreateRequestToIngredient(IngredientCreateRequest ingredientCreateRequest);
    IngredientResponse ingredientToIngredientResponse(Ingredient ingredient);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void ingredientUpdateRequestToIngredient(
            IngredientUpdateRequest ingredientUpdateRequest,
            @MappingTarget Ingredient ingredient
    );

    IngredientSummaryResponse ingredientToIngredientSummaryResponse(Ingredient ingredient);
}

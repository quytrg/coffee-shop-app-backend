package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.request.productvariantingredient.ProductVariantIngredientRequest;
import com.project.coffeeshopapp.dtos.response.productvariantingredient.ProductVariantIngredientResponse;
import com.project.coffeeshopapp.models.ProductVariantIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductVariantIngredientMapper {
    ProductVariantIngredient productVariantIngredientRequestToProductVariantIngredient(
            ProductVariantIngredientRequest productVariantIngredientRequest
    );
    @Mapping(source = "ingredient.id", target = "ingredientId")
    @Mapping(source = "ingredient.name", target = "ingredientName")
    ProductVariantIngredientResponse productVariantIngredientToProductVariantIngredientResponse(
            ProductVariantIngredient productVariantIngredient
    );
    List<ProductVariantIngredientResponse> productVariantIngredientsToProductVariantIngredientResponses(
            List<ProductVariantIngredient> productVariantIngredients
    );
}

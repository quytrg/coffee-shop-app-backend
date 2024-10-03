package com.project.coffeeshopapp.services.ingredient;

import com.project.coffeeshopapp.customexceptions.DataNotFoundException;
import com.project.coffeeshopapp.dtos.request.ingredient.IngredientCreateRequest;
import com.project.coffeeshopapp.dtos.request.ingredient.IngredientUpdateRequest;
import com.project.coffeeshopapp.dtos.response.ingredient.IngredientResponse;
import com.project.coffeeshopapp.mappers.IngredientMapper;
import com.project.coffeeshopapp.models.Ingredient;
import com.project.coffeeshopapp.repositories.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IngredientService implements IIngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Override
    @Transactional
    public IngredientResponse createIngredient(IngredientCreateRequest ingredientCreateRequest) {
        // map IngredientCreateRequest to Ingredient
        Ingredient ingredient = ingredientMapper.ingredientCreateRequestToIngredient(ingredientCreateRequest);
        // create ingredient
        Ingredient newIngredient = ingredientRepository.save(ingredient);
        // mapping new ingredient to dto response
        return ingredientMapper.ingredientToIngredientResponse(newIngredient);
    }

    @Override
    @Transactional
    public IngredientResponse updateIngredient(Long id, IngredientUpdateRequest ingredientUpdateRequest) {
        // check if ingredient id exists
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("ingredient", "Product not found with id: " + id));
        // map fields not null from ProductUpdateRequest to Product
        ingredientMapper.ingredientUpdateRequestToIngredient(ingredientUpdateRequest, ingredient);
        Ingredient updatedIngredient = ingredientRepository.save(ingredient);
        return ingredientMapper.ingredientToIngredientResponse(updatedIngredient);
    }
}

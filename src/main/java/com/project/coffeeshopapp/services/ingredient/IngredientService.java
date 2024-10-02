package com.project.coffeeshopapp.services.ingredient;

import com.project.coffeeshopapp.dtos.request.ingredient.IngredientCreateRequest;
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
}

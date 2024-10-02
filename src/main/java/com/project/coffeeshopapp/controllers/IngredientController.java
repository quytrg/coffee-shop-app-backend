package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.ingredient.IngredientCreateRequest;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.ingredient.IngredientResponse;
import com.project.coffeeshopapp.services.ingredient.IIngredientService;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("${api.prefix}/ingredients")
public class IngredientController {
    private final PaginationUtil paginationUtil;
    private final ResponseUtil responseUtil;
    private final IIngredientService ingredientService;

    @PostMapping()
    public ResponseEntity<SuccessResponse<IngredientResponse>> createIngredient(
            @RequestBody @Valid IngredientCreateRequest ingredientCreateRequest) {
        IngredientResponse ingredientResponse = ingredientService.createIngredient(ingredientCreateRequest);
        return responseUtil.createSuccessResponse(
                ingredientResponse,
                "Ingredient created successfully",
                HttpStatus.CREATED
        );
    }
}

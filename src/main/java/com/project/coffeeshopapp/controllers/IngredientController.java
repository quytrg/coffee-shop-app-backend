package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.ingredient.IngredientCreateRequest;
import com.project.coffeeshopapp.dtos.request.ingredient.IngredientSearchRequest;
import com.project.coffeeshopapp.dtos.request.ingredient.IngredientUpdateRequest;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.ingredient.IngredientResponse;
import com.project.coffeeshopapp.dtos.response.ingredient.IngredientSummaryResponse;
import com.project.coffeeshopapp.dtos.response.pagination.PaginationResponse;
import com.project.coffeeshopapp.services.ingredient.IIngredientService;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<IngredientResponse>> updateIngredient(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody IngredientUpdateRequest ingredientUpdateRequest) {
        IngredientResponse ingredientResponse = ingredientService.updateIngredient(id, ingredientUpdateRequest);
        return responseUtil.createSuccessResponse(
                ingredientResponse,
                "Ingredient updated successfully",
                HttpStatus.OK
        );
    }
    
    @GetMapping
    public ResponseEntity<SuccessResponse<PaginationResponse<IngredientSummaryResponse>>> getIngredients(
            @Valid @ModelAttribute IngredientSearchRequest request) {
        Page<IngredientSummaryResponse> ingredientSummaryResponsePage = ingredientService.getIngredients(request);
        PaginationResponse<IngredientSummaryResponse> paginationResponse = paginationUtil.createPaginationResponse(
                ingredientSummaryResponsePage
        );
        return responseUtil.createSuccessResponse(
                paginationResponse,
                "Retrieve paginated ingredients successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<IngredientResponse>> getIngredientById(
            @PathVariable(name = "id") Long id) {
        IngredientResponse ingredientResponse = ingredientService.getIngredientById(id);
        return responseUtil.createSuccessResponse(
                ingredientResponse,
                "Retrieve ingredient successfully",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> deleteIngredient(
            @PathVariable(name = "id") Long id) {
        ingredientService.softDeleteIngredient(id);
        return responseUtil.createSuccessResponseWithoutData(
                "Ingredient deleted successfully",
                HttpStatus.OK
        );
    }
}

package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.category.CategoryCreateRequest;
import com.project.coffeeshopapp.dtos.request.category.CategorySearchRequest;
import com.project.coffeeshopapp.dtos.request.category.CategoryUpdateRequest;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.category.CategoryResponse;
import com.project.coffeeshopapp.dtos.response.category.CategorySummaryResponse;
import com.project.coffeeshopapp.dtos.response.pagination.PaginationResponse;
import com.project.coffeeshopapp.properties.PaginationProperties;
import com.project.coffeeshopapp.services.category.ICategoryService;
import com.project.coffeeshopapp.services.role.RoleService;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.ResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService categoryService;
    private final PaginationUtil paginationUtil;
    private final ResponseUtil responseUtil;

    @PostMapping()
    public ResponseEntity<SuccessResponse<CategoryResponse>> createCategory(@RequestBody @Valid CategoryCreateRequest categoryCreateRequest) {
        CategoryResponse categoryResponse = categoryService.createCategory(categoryCreateRequest);
        return responseUtil.createSuccessResponse(
                categoryResponse,
                "Category was successfully created",
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<CategoryResponse>> updateCategory(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid CategoryUpdateRequest categoryUpdateRequest) {
        CategoryResponse categoryResponse = categoryService.updateCategory(id, categoryUpdateRequest);
        return responseUtil.createSuccessResponse(
                categoryResponse,
                "Category was successfully updated",
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<PaginationResponse<CategorySummaryResponse>>> getAllCategories(
            @Valid @ModelAttribute CategorySearchRequest request) {
        Page<CategorySummaryResponse> categorySummaryResponsePage = categoryService.getAllCategories(request);
        PaginationResponse<CategorySummaryResponse> paginationResponse = paginationUtil.createPaginationResponse(
                categorySummaryResponsePage
        );
        return responseUtil.createSuccessResponse(
                paginationResponse,
                "Retrieve paginated categories successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<CategoryResponse>> getCategoryById(
            @PathVariable(name = "id") Long id) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id);
        return responseUtil.createSuccessResponse(
                categoryResponse,
                "Get category successfully",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> deleteCategory(
            @PathVariable(name = "id") @PositiveOrZero Long id) {
        categoryService.softDeleteCategory(id);
        return responseUtil.createSuccessResponseWithoutData(
                "Category successfully deleted",
                HttpStatus.OK
        );
    }
}

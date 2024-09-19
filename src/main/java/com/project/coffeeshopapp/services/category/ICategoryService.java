package com.project.coffeeshopapp.services.category;

import com.project.coffeeshopapp.dtos.request.category.CategoryCreateRequest;
import com.project.coffeeshopapp.dtos.request.category.CategorySearchRequest;
import com.project.coffeeshopapp.dtos.request.category.CategoryUpdateRequest;
import com.project.coffeeshopapp.dtos.response.category.CategoryResponse;
import com.project.coffeeshopapp.dtos.response.category.CategorySummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ICategoryService {
    CategoryResponse createCategory(CategoryCreateRequest categoryCreateRequest);
    CategoryResponse updateCategory(Long id, CategoryUpdateRequest categoryUpdateRequest);
    Page<CategorySummaryResponse> getAllCategories(CategorySearchRequest request);
}

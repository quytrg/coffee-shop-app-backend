package com.project.coffeeshopapp.services.category;

import com.project.coffeeshopapp.dtos.request.category.CategoryCreateRequest;
import com.project.coffeeshopapp.dtos.request.category.CategoryUpdateRequest;
import com.project.coffeeshopapp.dtos.request.role.RoleUpdateRequest;
import com.project.coffeeshopapp.dtos.response.category.CategoryResponse;
import com.project.coffeeshopapp.dtos.response.role.RoleResponse;

public interface ICategoryService {
    CategoryResponse createCategory(CategoryCreateRequest categoryCreateRequest);
    CategoryResponse updateCategory(Long id, CategoryUpdateRequest categoryUpdateRequest);
}

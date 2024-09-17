package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.request.category.CategoryCreateRequest;
import com.project.coffeeshopapp.dtos.response.category.CategoryResponse;
import com.project.coffeeshopapp.models.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category categoryCreateRequestToCategory(CategoryCreateRequest categoryCreateRequest);

    CategoryResponse categoryToCategoryResponse(Category category);
}

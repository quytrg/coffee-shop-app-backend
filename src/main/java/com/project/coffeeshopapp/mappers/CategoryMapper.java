package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.request.category.CategoryCreateRequest;
import com.project.coffeeshopapp.dtos.request.category.CategoryUpdateRequest;
import com.project.coffeeshopapp.dtos.request.role.RoleUpdateRequest;
import com.project.coffeeshopapp.dtos.response.category.CategoryResponse;
import com.project.coffeeshopapp.dtos.response.category.CategorySummaryResponse;
import com.project.coffeeshopapp.models.Category;
import com.project.coffeeshopapp.models.Role;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category categoryCreateRequestToCategory(CategoryCreateRequest categoryCreateRequest);

    CategoryResponse categoryToCategoryResponse(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) // ignore null value
    void categoryUpdateRequestToCategory(CategoryUpdateRequest categoryUpdateRequest, @MappingTarget Category category);

    CategorySummaryResponse categoryToCategorySummaryResponse(Category category);
}

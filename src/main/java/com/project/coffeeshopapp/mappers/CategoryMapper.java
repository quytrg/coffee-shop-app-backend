package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.request.category.CategoryCreateRequest;
import com.project.coffeeshopapp.dtos.request.category.CategoryUpdateRequest;
import com.project.coffeeshopapp.dtos.response.category.CategoryResponse;
import com.project.coffeeshopapp.dtos.response.category.CategorySummaryResponse;
import com.project.coffeeshopapp.models.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { ImageMapper.class })
public interface CategoryMapper {
    @Mapping(target = "images", ignore = true)
    Category categoryCreateRequestToCategory(CategoryCreateRequest categoryCreateRequest);

    @Mapping(source = "images", target = "images")
    CategoryResponse categoryToCategoryResponse(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) // ignore null value
    @Mapping(target = "images", ignore = true)
    void categoryUpdateRequestToCategory(CategoryUpdateRequest categoryUpdateRequest, @MappingTarget Category category);
    @Mapping(source = "images", target = "imageUrls")
    CategorySummaryResponse categoryToCategorySummaryResponse(Category category);
}

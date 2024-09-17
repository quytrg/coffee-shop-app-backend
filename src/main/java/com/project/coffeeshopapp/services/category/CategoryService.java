package com.project.coffeeshopapp.services.category;

import com.project.coffeeshopapp.dtos.request.category.CategoryCreateRequest;
import com.project.coffeeshopapp.dtos.response.category.CategoryResponse;
import com.project.coffeeshopapp.mappers.CategoryMapper;
import com.project.coffeeshopapp.models.Category;
import com.project.coffeeshopapp.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryCreateRequest categoryCreateRequest) {
        Category newCategory = categoryMapper.categoryCreateRequestToCategory(categoryCreateRequest);
        Category savedCategory = categoryRepository.save(newCategory);
        return categoryMapper.categoryToCategoryResponse(savedCategory);
    }
}

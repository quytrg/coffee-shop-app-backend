package com.project.coffeeshopapp.services.category;

import com.project.coffeeshopapp.customexceptions.DataNotFoundException;
import com.project.coffeeshopapp.dtos.request.category.CategoryCreateRequest;
import com.project.coffeeshopapp.dtos.request.category.CategoryUpdateRequest;
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

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest categoryUpdateRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("category" ,"Category not found with id: " + id));
        categoryMapper.categoryUpdateRequestToCategory(categoryUpdateRequest, category);
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryResponse(updatedCategory);
    }
}

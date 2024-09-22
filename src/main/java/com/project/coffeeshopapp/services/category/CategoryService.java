package com.project.coffeeshopapp.services.category;

import com.project.coffeeshopapp.customexceptions.DataNotFoundException;
import com.project.coffeeshopapp.dtos.request.category.CategoryCreateRequest;
import com.project.coffeeshopapp.dtos.request.category.CategorySearchRequest;
import com.project.coffeeshopapp.dtos.request.category.CategoryUpdateRequest;
import com.project.coffeeshopapp.dtos.response.category.CategoryResponse;
import com.project.coffeeshopapp.dtos.response.category.CategorySummaryResponse;
import com.project.coffeeshopapp.enums.CategoryStatus;
import com.project.coffeeshopapp.mappers.CategoryMapper;
import com.project.coffeeshopapp.models.Category;
import com.project.coffeeshopapp.repositories.CategoryRepository;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final PaginationUtil paginationUtil;
    private final SortUtil sortUtil;

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

    @Override
    public Page<CategorySummaryResponse> getAllCategories(CategorySearchRequest request) {
        Sort sort = sortUtil.createSort(
                request.getSortBy(),
                request.getSortDir()
        );
        Pageable pageable = paginationUtil.createPageable(
                request.getPage(),
                request.getSize(),
                sort
        );
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return categoryPage.map(categoryMapper::categoryToCategorySummaryResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("category" ,"Category not found with id: " + id));
        return categoryMapper.categoryToCategoryResponse(category);
    }

    @Override
    @Transactional
    public void softDeleteCategory(Long id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("category" ,"Category not found with id: " + id));
        categoryRepository.softDelete(id);
    }
}

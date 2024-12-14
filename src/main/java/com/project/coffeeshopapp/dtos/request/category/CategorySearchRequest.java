package com.project.coffeeshopapp.dtos.request.category;

import com.project.coffeeshopapp.dtos.request.base.BasePaginationSortRequest;
import com.project.coffeeshopapp.enums.CategorySortField;
import com.project.coffeeshopapp.enums.CategoryStatus;
import com.project.coffeeshopapp.enums.SortDirection;
import jakarta.annotation.PostConstruct;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class CategorySearchRequest extends BasePaginationSortRequest<CategorySortField> {
    // Additional search-specific fields
    private String keyword;
    private CategoryStatus status;

    @Override
    protected List<CategorySortField> initSortBy() {
        return Collections.singletonList(CategorySortField.NAME);
    }

    @Override
    protected List<SortDirection> initSortDir() {
        return Collections.singletonList(SortDirection.ASC);
    }
}

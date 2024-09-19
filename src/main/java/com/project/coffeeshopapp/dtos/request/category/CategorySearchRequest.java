package com.project.coffeeshopapp.dtos.request.category;

import com.project.coffeeshopapp.dtos.request.base.BasePaginationSortRequest;
import com.project.coffeeshopapp.enums.CategorySortField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collections;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CategorySearchRequest extends BasePaginationSortRequest<CategorySortField> {
    // Additional search-specific fields
    private String keyword;
    private String status;

    @Override
    protected void initSortBy() {
        if (getSortBy() == null || getSortBy().isEmpty()) {
            setSortBy(Collections.singletonList(CategorySortField.NAME));
        }
    }
}

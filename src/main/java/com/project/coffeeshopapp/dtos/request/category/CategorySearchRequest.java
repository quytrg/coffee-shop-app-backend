package com.project.coffeeshopapp.dtos.request.category;

import com.project.coffeeshopapp.dtos.request.base.BasePaginationSortRequest;
import com.project.coffeeshopapp.enums.CategorySortField;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CategorySearchRequest extends BasePaginationSortRequest<CategorySortField> {
    // Additional search-specific fields
    private String keyword;

    @Override
    protected List<CategorySortField> initSortBy() {
        return Collections.singletonList(CategorySortField.NAME);
    }
}

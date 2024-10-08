package com.project.coffeeshopapp.dtos.request.ingredient;

import com.project.coffeeshopapp.dtos.request.base.BasePaginationSortRequest;
import com.project.coffeeshopapp.enums.IngredientSortField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class IngredientSearchRequest extends BasePaginationSortRequest<IngredientSortField> {
    // Additional search-specific fields
    private String keyword;
    @Override
    protected List<IngredientSortField> initSortBy() {
        return Collections.singletonList(IngredientSortField.NAME);
    }
}

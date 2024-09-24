package com.project.coffeeshopapp.dtos.request.product;

import com.project.coffeeshopapp.dtos.request.base.BasePaginationSortRequest;
import com.project.coffeeshopapp.enums.ProductSortField;
import com.project.coffeeshopapp.enums.RoleSortField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class ProductSearchRequest extends BasePaginationSortRequest<ProductSortField> {
    // Additional search-specific fields
    private String keyword;
    @Override
    protected List<ProductSortField> initSortBy() {
        return Collections.singletonList(ProductSortField.NAME);
    }
}

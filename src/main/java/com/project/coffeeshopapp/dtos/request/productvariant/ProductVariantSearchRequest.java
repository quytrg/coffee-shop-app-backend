package com.project.coffeeshopapp.dtos.request.productvariant;

import com.project.coffeeshopapp.dtos.request.base.BasePaginationSortRequest;
import com.project.coffeeshopapp.enums.ProductStatus;
import com.project.coffeeshopapp.enums.ProductVariantSortField;
import com.project.coffeeshopapp.enums.SortDirection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class ProductVariantSearchRequest extends BasePaginationSortRequest<ProductVariantSortField> {
    // Additional search-specific fields
    private String keyword;
    private ProductStatus status;
    @Override
    protected List<ProductVariantSortField> initSortBy() {
        return Collections.singletonList(ProductVariantSortField.PRICE);
    }
    @Override
    protected List<SortDirection> initSortDir() {
        return Collections.singletonList(SortDirection.ASC);
    }
}

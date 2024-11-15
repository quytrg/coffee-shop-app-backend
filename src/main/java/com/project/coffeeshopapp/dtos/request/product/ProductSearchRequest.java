package com.project.coffeeshopapp.dtos.request.product;

import com.project.coffeeshopapp.dtos.request.base.BasePaginationSortRequest;
import com.project.coffeeshopapp.enums.ProductSortField;
import com.project.coffeeshopapp.enums.ProductStatus;
import com.project.coffeeshopapp.enums.SortDirection;
import jakarta.validation.constraints.PositiveOrZero;
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
    private String keyword; // productName, categoryName, description
    private List<Long> categoryIds;
    private ProductStatus status;
    private Long minPosition;
    private Long maxPosition;

    @Override
    protected List<ProductSortField> initSortBy() {
        return Collections.singletonList(ProductSortField.UPDATED_AT);
    }
    @Override
    protected List<SortDirection> initSortDir() {
        return Collections.singletonList(SortDirection.DESC);
    }
}

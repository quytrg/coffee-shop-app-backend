package com.project.coffeeshopapp.dtos.request.supplier;

import com.project.coffeeshopapp.dtos.request.base.BasePaginationSortRequest;
import com.project.coffeeshopapp.enums.RoleSortField;
import com.project.coffeeshopapp.enums.SortDirection;
import com.project.coffeeshopapp.enums.SupplierSortField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class SupplierSearchRequest extends BasePaginationSortRequest<SupplierSortField> {
    // Additional search-specific fields
    private String keyword;
    private Double minRating;
    private Double maxRating;
    @Override
    protected List<SupplierSortField> initSortBy() {
        return Collections.singletonList(SupplierSortField.NAME);
    }
    @Override
    protected List<SortDirection> initSortDir() {
        return Collections.singletonList(SortDirection.ASC);
    }
}

package com.project.coffeeshopapp.dtos.request.role;

import com.project.coffeeshopapp.dtos.request.base.BasePaginationSortRequest;
import com.project.coffeeshopapp.enums.CategorySortField;
import com.project.coffeeshopapp.enums.RoleSortField;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class RoleSearchRequest extends BasePaginationSortRequest<RoleSortField> {
    // Additional search-specific fields
    private String keyword;
    @Override
    protected List<RoleSortField> initSortBy() {
        return Collections.singletonList(RoleSortField.NAME);
    }
}
package com.project.coffeeshopapp.dtos.request.role;

import com.project.coffeeshopapp.dtos.request.base.BasePaginationSortRequest;
import com.project.coffeeshopapp.enums.CategorySortField;
import com.project.coffeeshopapp.enums.RoleSortField;
import com.project.coffeeshopapp.enums.RoleStatus;
import com.project.coffeeshopapp.enums.SortDirection;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class RoleSearchRequest extends BasePaginationSortRequest<RoleSortField> {
    // Additional search-specific fields
    private String keyword; // name, description
    private RoleStatus status;
    private List<Long> permissionIds;

    @Override
    protected List<RoleSortField> initSortBy() {
        return Collections.singletonList(RoleSortField.NAME);
    }
    @Override
    protected List<SortDirection> initSortDir() {
        return Collections.singletonList(SortDirection.ASC);
    }
}
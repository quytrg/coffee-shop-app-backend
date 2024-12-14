package com.project.coffeeshopapp.dtos.request.permission;

import com.project.coffeeshopapp.dtos.request.base.BasePaginationSortRequest;
import com.project.coffeeshopapp.enums.PermissionSortField;
import com.project.coffeeshopapp.enums.SortDirection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class PermissionSearchRequest extends BasePaginationSortRequest<PermissionSortField> {
    // Additional search-specific fields
    private String keyword; // name, description

    @Override
    protected List<PermissionSortField> initSortBy() {
        return Collections.singletonList(PermissionSortField.NAME);
    }

    @Override
    protected List<SortDirection> initSortDir() {
        return Collections.singletonList(SortDirection.ASC);
    }
}

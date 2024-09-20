package com.project.coffeeshopapp.dtos.request.role;

import com.project.coffeeshopapp.dtos.request.base.BasePaginationSortRequest;
import com.project.coffeeshopapp.enums.CategorySortField;
import com.project.coffeeshopapp.enums.RoleSortField;
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
public class RoleSearchRequest extends BasePaginationSortRequest<RoleSortField> {
    // Additional search-specific fields
    private String keyword;
    @Override
    protected List<RoleSortField> initSortBy() {
        return Collections.singletonList(RoleSortField.NAME);
    }
}
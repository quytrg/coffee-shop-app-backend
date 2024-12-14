package com.project.coffeeshopapp.dtos.request.user;

import com.project.coffeeshopapp.dtos.request.base.BasePaginationSortRequest;
import com.project.coffeeshopapp.enums.SortDirection;
import com.project.coffeeshopapp.enums.UserSortField;
import com.project.coffeeshopapp.enums.UserStatus;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class UserSearchRequest extends BasePaginationSortRequest<UserSortField> {
    // Additional search-specific fields
    private String keyword;
    private UserStatus status;
    private Boolean sex;
    private Long roleId;
    private List<Long> roleIds = new ArrayList<>();

    @Override
    protected List<UserSortField> initSortBy() {
        return Collections.singletonList(UserSortField.FULL_NAME);
    }
    @Override
    protected List<SortDirection> initSortDir() {
        return Collections.singletonList(SortDirection.ASC);
    }
}
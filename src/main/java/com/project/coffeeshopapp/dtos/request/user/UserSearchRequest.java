package com.project.coffeeshopapp.dtos.request.user;

import com.project.coffeeshopapp.dtos.request.base.BasePaginationSortRequest;
import com.project.coffeeshopapp.enums.UserSortField;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class UserSearchRequest extends BasePaginationSortRequest<UserSortField> {
    // Additional search-specific fields
    private String keyword;
    @Override
    protected List<UserSortField> initSortBy() {
        return Collections.singletonList(UserSortField.FULL_NAME);
    }
}
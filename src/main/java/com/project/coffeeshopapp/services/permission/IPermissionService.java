package com.project.coffeeshopapp.services.permission;

import com.project.coffeeshopapp.dtos.request.permission.PermissionSearchRequest;
import com.project.coffeeshopapp.dtos.response.permission.PermissionSummaryResponse;
import org.springframework.data.domain.Page;

public interface IPermissionService {
    Page<PermissionSummaryResponse> getPermissions(PermissionSearchRequest permissionSearchRequest);
}

package com.project.coffeeshopapp.services.role;

import com.project.coffeeshopapp.dtos.request.role.RoleCreateRequest;
import com.project.coffeeshopapp.dtos.request.role.RoleUpdateRequest;
import com.project.coffeeshopapp.dtos.response.role.RoleResponse;
import com.project.coffeeshopapp.dtos.response.role.RoleSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoleService {
    RoleResponse createRole(RoleCreateRequest roleCreateRequest);
    RoleResponse updateRole(Long id, RoleUpdateRequest roleUpdateRequest);
    Page<RoleSummaryResponse> getAllRoles(Pageable pageable);
    RoleResponse getRoleWithPermissions(Long id);
    void softDeleteRole(Long id);
}

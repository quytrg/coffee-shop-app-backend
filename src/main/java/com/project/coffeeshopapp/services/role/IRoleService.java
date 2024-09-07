package com.project.coffeeshopapp.services.role;

import com.project.coffeeshopapp.dtos.request.role.RoleCreateRequest;
import com.project.coffeeshopapp.dtos.request.role.RoleUpdateRequest;
import com.project.coffeeshopapp.dtos.response.role.RoleResponse;
import com.project.coffeeshopapp.models.Role;

import java.util.List;

public interface IRoleService {
    RoleResponse createRole(RoleCreateRequest roleCreateRequest);
    RoleResponse updateRole(Long id, RoleUpdateRequest roleUpdateRequest);
}

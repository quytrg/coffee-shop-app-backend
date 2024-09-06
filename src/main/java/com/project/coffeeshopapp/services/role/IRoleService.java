package com.project.coffeeshopapp.services.role;

import com.project.coffeeshopapp.dtos.request.role.RoleCreateRequest;
import com.project.coffeeshopapp.dtos.response.role.RoleResponse;

public interface IRoleService {
    RoleResponse createRole(RoleCreateRequest roleCreateRequest);
}

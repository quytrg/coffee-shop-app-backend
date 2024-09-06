package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.request.role.RoleCreateRequest;
import com.project.coffeeshopapp.dtos.response.permission.PermissionResponse;
import com.project.coffeeshopapp.dtos.response.role.RoleResponse;
import com.project.coffeeshopapp.models.Permission;
import com.project.coffeeshopapp.models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    // ignore permissions to check if they exist in db
    @Mapping(target = "permissions", ignore = true)
    Role roleCreateRequestToRole(RoleCreateRequest roleCreateRequest);
    RoleResponse roleToRoleResponse(Role role);
    // mapping bean that is child bean of Role and RoleResponse (Permission -> PermissionResponse)
    PermissionResponse permissionToPermissionResponse(Permission permission);
}

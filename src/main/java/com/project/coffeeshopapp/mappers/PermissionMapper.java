package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.response.permission.PermissionSummaryResponse;
import com.project.coffeeshopapp.models.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionSummaryResponse permissionToPermissionSummaryResponse(Permission permission);
}

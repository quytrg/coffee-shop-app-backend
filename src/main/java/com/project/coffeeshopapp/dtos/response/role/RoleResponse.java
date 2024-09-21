package com.project.coffeeshopapp.dtos.response.role;

import com.project.coffeeshopapp.dtos.response.permission.PermissionResponse;
import com.project.coffeeshopapp.enums.RoleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    private Long id;
    private String name;
    private String description;
    private Set<PermissionResponse> permissions;
    private RoleStatus status;
}

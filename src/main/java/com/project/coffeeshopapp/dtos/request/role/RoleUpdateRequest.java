package com.project.coffeeshopapp.dtos.request.role;

import com.project.coffeeshopapp.customannotations.UniqueRoleName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleUpdateRequest {
    @Size(min = 2, max = 20)
    private String name;

    private String description;

    // only check if permissionIds exists
    @Size(min = 1, message = "Permission ids must contain at least one value")
    private Set<Long> permissionIds;
}
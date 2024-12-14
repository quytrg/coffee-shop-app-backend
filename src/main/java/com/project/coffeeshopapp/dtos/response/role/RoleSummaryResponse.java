package com.project.coffeeshopapp.dtos.response.role;

import com.project.coffeeshopapp.enums.RoleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleSummaryResponse {
    private Long id;
    private String name;
    private String description;
    private RoleStatus status;
}

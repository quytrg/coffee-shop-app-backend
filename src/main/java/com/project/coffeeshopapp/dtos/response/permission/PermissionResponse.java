package com.project.coffeeshopapp.dtos.response.permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionResponse {
    private Long id;
    private String name;
    private String description;
}
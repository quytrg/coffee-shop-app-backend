package com.project.coffeeshopapp.dtos.response.user;

import com.project.coffeeshopapp.dtos.response.image.ImageSummaryResponse;
import com.project.coffeeshopapp.dtos.response.permission.PermissionResponse;
import com.project.coffeeshopapp.dtos.response.role.RoleSummaryResponse;
import com.project.coffeeshopapp.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private UserStatus status;
    private RoleSummaryResponse role;
    private List<ImageSummaryResponse> images = new ArrayList<>();
    private Set<PermissionResponse> permissions = new HashSet<>();
}

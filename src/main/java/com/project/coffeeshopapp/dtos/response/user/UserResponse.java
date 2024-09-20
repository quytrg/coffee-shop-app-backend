package com.project.coffeeshopapp.dtos.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.coffeeshopapp.dtos.response.role.RoleSummaryResponse;
import com.project.coffeeshopapp.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;

    private String fullName;

    private String phoneNumber;

    private String email;

    private String address;

    private UserStatus status;

    private Boolean sex;

    private String dateOfBirth;

    private RoleSummaryResponse role;
}

package com.project.coffeeshopapp.dtos.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private int id;

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String email;

    private String address;

    @JsonProperty("is_active")
    private Boolean isActive;

    private Boolean sex;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty("role_id")
    private Long roleId;
}

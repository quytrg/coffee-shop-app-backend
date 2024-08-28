package com.project.coffeeshopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Email can not be blank")
    private String email;

    private String address;

    @NotBlank(message = "Password can not be blank")
    private String password;

    @JsonProperty("retype_password")
    @NotBlank(message = "Retype password can not be blank")
    private String retypePassword;

    private Boolean sex;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @NotNull(message = "Role id is required")
    @JsonProperty("role_id")
    private Long roleId;
}

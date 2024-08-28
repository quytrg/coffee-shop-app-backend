package com.project.coffeeshopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.customannotations.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @JsonProperty("fullname")
    @Size(min = 3, max = 200)
    private String fullName;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    @Size(min = 9, max = 10)
    private String phoneNumber;

    @NotBlank(message = "Email can not be blank")
    @Size(min = 5, max = 100)
    @ValidEmail
    private String email;

    @Size(max = 200)
    private String address;

    @NotBlank(message = "Password can not be blank")
    @Size(min = 4, max = 50)
    private String password;

    @JsonProperty("retype_password")
    @NotBlank(message = "Retype password can not be blank")
    @Size(min = 4, max = 50)
    private String retypePassword;

    private Boolean sex;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @NotNull(message = "Role id is required")
    @JsonProperty("role_id")
    private Long roleId;
}

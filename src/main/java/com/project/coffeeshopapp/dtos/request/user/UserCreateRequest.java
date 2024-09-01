package com.project.coffeeshopapp.dtos.request.user;

import com.project.coffeeshopapp.customannotations.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
    @Size(min = 3, max = 200)
    private String fullName;

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

    @NotBlank(message = "Retype password can not be blank")
    @Size(min = 4, max = 50)
    private String retypePassword;

    private Boolean isActive;

    private Boolean sex;

    private String dateOfBirth;

    @NotNull(message = "Role id is required")
    private Long roleId;
}

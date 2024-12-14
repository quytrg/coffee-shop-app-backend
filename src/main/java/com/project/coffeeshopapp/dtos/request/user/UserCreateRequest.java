package com.project.coffeeshopapp.dtos.request.user;

import com.project.coffeeshopapp.customannotations.PasswordMatches;
import com.project.coffeeshopapp.customannotations.UniquePhoneNumber;
import com.project.coffeeshopapp.customannotations.ValidEmail;
import com.project.coffeeshopapp.enums.UserStatus;
import com.project.coffeeshopapp.validationservices.contracts.PasswordMatchingCheckable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class UserCreateRequest implements PasswordMatchingCheckable {
    @Size(min = 3, max = 200)
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Size(min = 9, max = 10)
    @UniquePhoneNumber
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

    @NotNull(message = "User status is required")
    private UserStatus status;

    private Boolean sex;

    private LocalDateTime dateOfBirth;

    @NotNull(message = "Role id is required")
    private Long roleId;

    @NotEmpty(message = "Image ids cannot be empty")
    @Size(min = 1, message = "Image ids must contain at least one value")
    private List<Long> imageIds = new ArrayList<>();
}

package com.project.coffeeshopapp.dtos.request.user;

import com.project.coffeeshopapp.customannotations.PasswordMatches;
import com.project.coffeeshopapp.customannotations.UniquePhoneNumber;
import com.project.coffeeshopapp.customannotations.ValidEmail;
import com.project.coffeeshopapp.validationservices.contracts.PasswordMatchingCheckable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class UserUpdateRequest implements PasswordMatchingCheckable {
    @Size(min = 1, max = 30, message = "Full name length must be between {min} and {max} characters")
    private String fullName;

    @Size(min = 9, max = 10, message = "Phone number length must be between {min} and {max} digits")
    @UniquePhoneNumber
    private String phoneNumber;

    @Size(min = 5, max = 100, message = "Email length must be between {min} and {max} characters")
    @ValidEmail
    private String email;

    @Size(max = 200)
    private String address;

    @Size(min = 4, max = 50, message = "Password length must be between {min} and {max} characters")
    private String password;

    @Size(min = 4, max = 50, message = "Retype password length must be between {min} and {max} characters")
    private String retypePassword;

    private Boolean isActive;

    private Boolean sex;

    private String dateOfBirth;

    private Long roleId;
}

package com.project.coffeeshopapp.validationservices.user;

import com.project.coffeeshopapp.dtos.UserDTO;

public interface IUserValidationService {
    void validateUserForCreation(UserDTO userDTO);
}

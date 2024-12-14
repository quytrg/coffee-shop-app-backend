package com.project.coffeeshopapp.validationservices.user;

import com.project.coffeeshopapp.dtos.request.user.UserCreateRequest;
import com.project.coffeeshopapp.dtos.request.user.UserUpdateRequest;

public interface IUserValidationService {
    void validateUserForCreation(UserCreateRequest userCreateRequest);
    void validateUserForUpdate(UserUpdateRequest userUpdateRequest);
}

package com.project.coffeeshopapp.validationservices.user;

import com.project.coffeeshopapp.dtos.request.user.UserCreateRequest;

public interface IUserValidationService {
    void validateUserForCreation(UserCreateRequest userCreateRequest);
}

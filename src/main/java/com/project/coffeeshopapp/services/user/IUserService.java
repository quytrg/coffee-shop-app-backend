package com.project.coffeeshopapp.services.user;

import com.project.coffeeshopapp.dtos.request.user.UserCreateRequest;
import com.project.coffeeshopapp.dtos.response.user.UserResponse;

public interface IUserService {
    UserResponse createUser(UserCreateRequest userCreateRequest);
}

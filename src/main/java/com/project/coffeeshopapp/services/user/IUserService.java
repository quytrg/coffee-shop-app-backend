package com.project.coffeeshopapp.services.user;

import com.project.coffeeshopapp.dtos.request.user.UserCreateRequest;
import com.project.coffeeshopapp.dtos.request.user.UserLoginRequest;
import com.project.coffeeshopapp.dtos.request.user.UserUpdateRequest;
import com.project.coffeeshopapp.dtos.response.jwt.JwtResponse;
import com.project.coffeeshopapp.dtos.response.user.UserResponse;

public interface IUserService {
    UserResponse createUser(UserCreateRequest userCreateRequest);
    JwtResponse login(String phoneNumber, String password);
    UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest);
}

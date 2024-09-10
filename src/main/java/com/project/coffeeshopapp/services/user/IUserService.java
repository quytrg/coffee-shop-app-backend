package com.project.coffeeshopapp.services.user;

import com.project.coffeeshopapp.dtos.request.user.UserCreateRequest;
import com.project.coffeeshopapp.dtos.request.user.UserUpdateRequest;
import com.project.coffeeshopapp.dtos.response.jwt.JwtResponse;
import com.project.coffeeshopapp.dtos.response.user.UserResponse;
import com.project.coffeeshopapp.dtos.response.user.UserSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    UserResponse createUser(UserCreateRequest userCreateRequest);
    JwtResponse login(String phoneNumber, String password);
    UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest);
    Page<UserSummaryResponse> getAllUsers(Pageable pageable);
    void softDeleteUser(Long id);
}

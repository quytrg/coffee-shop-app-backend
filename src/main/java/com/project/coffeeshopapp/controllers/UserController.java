package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.user.UserCreateRequest;
import com.project.coffeeshopapp.dtos.request.user.UserLoginRequest;
import com.project.coffeeshopapp.dtos.request.user.UserUpdateRequest;
import com.project.coffeeshopapp.dtos.response.jwt.JwtResponse;
import com.project.coffeeshopapp.dtos.response.user.UserResponse;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.services.user.IUserService;
import com.project.coffeeshopapp.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final IUserService userService;
    private final ResponseUtil responseUtil;

    @PostMapping()
    public ResponseEntity<SuccessResponse<UserResponse>> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        UserResponse userResponse = userService.createUser(userCreateRequest);
        return responseUtil.createSuccessResponse(
                userResponse,
                "User was successfully created",
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<JwtResponse>> login(
            @Valid @RequestBody UserLoginRequest userLoginRequest) {
        JwtResponse jwtResponse = userService.login(userLoginRequest.getPhoneNumber(), userLoginRequest.getPassword());
        return responseUtil.createSuccessResponse(
                jwtResponse,
                "Login successful",
                HttpStatus.OK
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<UserResponse>> updateUser(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        UserResponse userResponse = userService.updateUser(id, userUpdateRequest);
        return responseUtil.createSuccessResponse(
                userResponse,
                "User was successfully updated",
                HttpStatus.OK
        );
    }
}
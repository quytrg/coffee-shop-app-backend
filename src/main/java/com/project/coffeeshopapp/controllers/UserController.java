package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.user.UserCreateRequest;
import com.project.coffeeshopapp.dtos.request.user.UserLoginRequest;
import com.project.coffeeshopapp.dtos.response.jwt.JwtResponse;
import com.project.coffeeshopapp.dtos.response.user.UserResponse;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.services.user.IUserService;
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

    @PostMapping()
    public ResponseEntity<SuccessResponse<?>> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest){
        UserResponse userResponse = userService.createUser(userCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("User is successfully created")
                        .data(userResponse)
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<JwtResponse>> login(
            @Valid @RequestBody UserLoginRequest userLoginRequest) {
            JwtResponse jwtResponse = userService.login(userLoginRequest.getPhoneNumber(), userLoginRequest.getPassword());
            return ResponseEntity.ok(
                    SuccessResponse.<JwtResponse>builder()
                            .status(HttpStatus.OK.value())
                            .message("Login successful")
                            .data(jwtResponse)
                            .build()
            );
    }
}
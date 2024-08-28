package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.UserDTO;
import com.project.coffeeshopapp.models.User;
import com.project.coffeeshopapp.responses.SuccessResponse;
import com.project.coffeeshopapp.services.user.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final IUserService userService;

    @PostMapping()
    public ResponseEntity<SuccessResponse<?>> createUser(@Valid @RequestBody UserDTO userDTO){
        User user = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("User is successfully created")
                        .build()
        );
    }
}
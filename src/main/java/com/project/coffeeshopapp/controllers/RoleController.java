package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.role.RoleCreateRequest;
import com.project.coffeeshopapp.dtos.request.role.RoleUpdateRequest;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.role.RoleResponse;
import com.project.coffeeshopapp.dtos.response.user.UserResponse;
import com.project.coffeeshopapp.services.role.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/roles")
public class RoleController {
    private final RoleService roleService;

    @PostMapping()
    public ResponseEntity<SuccessResponse<?>> createRole(@RequestBody @Valid RoleCreateRequest roleCreateRequest) {
        RoleResponse roleResponse = roleService.createRole(roleCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Role was successfully created")
                        .data(roleResponse)
                        .build()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> updateRole(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid RoleUpdateRequest roleUpdateRequest) {
        RoleResponse roleResponse = roleService.updateRole(id, roleUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Role was successfully updated")
                        .data(roleResponse)
                        .build()
        );
    }
}

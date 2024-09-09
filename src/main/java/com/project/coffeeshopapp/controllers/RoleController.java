package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.role.RoleCreateRequest;
import com.project.coffeeshopapp.dtos.request.role.RoleUpdateRequest;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.pagination.PaginationResponse;
import com.project.coffeeshopapp.dtos.response.role.RoleResponse;
import com.project.coffeeshopapp.dtos.response.role.RoleSummaryResponse;
import com.project.coffeeshopapp.services.role.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @GetMapping
    public ResponseEntity<SuccessResponse<PaginationResponse<?>>> getAllRoles(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        // validate page and size
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("page and size must be non-negative.");
        }

        PageRequest pageRequest = PageRequest.of(
                page, size,
                Sort.by("name")
        );
        Page<RoleSummaryResponse> roleSummaryResponsePage = roleService.getAllRoles(pageRequest);

        PaginationResponse<RoleSummaryResponse> paginationResponse = PaginationResponse.<RoleSummaryResponse>builder()
                .content(roleSummaryResponsePage.getContent())
                .page(PaginationResponse.PageInfo.builder()
                        .number(page)
                        .size(size)
                        .totalElements(roleSummaryResponsePage.getTotalElements())
                        .totalPages(roleSummaryResponsePage.getTotalPages())
                        .build())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.<PaginationResponse<?>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Retrieve all roles successfully")
                        .data(paginationResponse)
                        .build()
        );
    }
}

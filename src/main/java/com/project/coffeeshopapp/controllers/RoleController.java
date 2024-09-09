package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.role.RoleCreateRequest;
import com.project.coffeeshopapp.dtos.request.role.RoleUpdateRequest;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.pagination.PaginationResponse;
import com.project.coffeeshopapp.dtos.response.role.RoleResponse;
import com.project.coffeeshopapp.dtos.response.role.RoleSummaryResponse;
import com.project.coffeeshopapp.services.role.RoleService;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.ResponseUtil;
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
    private final PaginationUtil paginationUtil;
    private final ResponseUtil responseUtil;

    @PostMapping()
    public ResponseEntity<SuccessResponse<RoleResponse>> createRole(@RequestBody @Valid RoleCreateRequest roleCreateRequest) {
        RoleResponse roleResponse = roleService.createRole(roleCreateRequest);
        return responseUtil.createSuccessResponse(
                roleResponse,
                "Role was successfully created",
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<RoleResponse>> updateRole(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid RoleUpdateRequest roleUpdateRequest) {
        RoleResponse roleResponse = roleService.updateRole(id, roleUpdateRequest);
        return responseUtil.createSuccessResponse(
                roleResponse,
                "Role was successfully updated",
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<PaginationResponse<RoleSummaryResponse>>> getAllRoles(
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

        PaginationResponse<RoleSummaryResponse> paginationResponse = paginationUtil.createPaginationResponse(
                roleSummaryResponsePage, page, size
        );

        return responseUtil.createSuccessResponse(
                paginationResponse,
                "Retrieve all roles successfully",
                HttpStatus.OK
        );
    }
}

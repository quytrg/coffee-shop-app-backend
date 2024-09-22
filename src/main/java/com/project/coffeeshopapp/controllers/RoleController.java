package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.role.RoleCreateRequest;
import com.project.coffeeshopapp.dtos.request.role.RoleSearchRequest;
import com.project.coffeeshopapp.dtos.request.role.RoleUpdateRequest;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.pagination.PaginationResponse;
import com.project.coffeeshopapp.dtos.response.role.RoleResponse;
import com.project.coffeeshopapp.dtos.response.role.RoleSummaryResponse;
import com.project.coffeeshopapp.services.role.IRoleService;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("${api.prefix}/roles")
public class RoleController {
    private final IRoleService roleService;
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
            @Valid @ModelAttribute RoleSearchRequest request) {
        Page<RoleSummaryResponse> roleSummaryResponsePage = roleService.getAllRoles(request);
        PaginationResponse<RoleSummaryResponse> paginationResponse = paginationUtil.createPaginationResponse(
                roleSummaryResponsePage
        );
        return responseUtil.createSuccessResponse(
                paginationResponse,
                "Retrieve paginated roles successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<RoleResponse>> getRoleWithPermissions(
            @PathVariable(name = "id") Long id) {
        RoleResponse roleResponse = roleService.getRoleWithPermissions(id);
        return responseUtil.createSuccessResponse(
                roleResponse,
                "Get role successfully",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> deleteRole(@PathVariable(name = "id") Long id) {
        roleService.softDeleteRole(id);
        return responseUtil.createSuccessResponseWithoutData(
                "Role successfully deleted",
                HttpStatus.OK
        );
    }
}

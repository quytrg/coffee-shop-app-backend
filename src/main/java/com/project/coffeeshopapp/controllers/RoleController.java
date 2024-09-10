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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
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
            @RequestParam(name = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
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

package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.permission.PermissionSearchRequest;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.pagination.PaginationResponse;
import com.project.coffeeshopapp.dtos.response.permission.PermissionSummaryResponse;
import com.project.coffeeshopapp.services.permission.IPermissionService;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("${api.prefix}/permissions")
public class PermissionController {
    private final ResponseUtil responseUtil;
    private final PaginationUtil paginationUtil;
    private final IPermissionService permissionService;

    @GetMapping
    public ResponseEntity<SuccessResponse<PaginationResponse<PermissionSummaryResponse>>> getPermissions(
            @Valid @ModelAttribute PermissionSearchRequest request) {
        Page<PermissionSummaryResponse> permissionSummaryResponsePage = permissionService.getPermissions(request);
        PaginationResponse<PermissionSummaryResponse> paginationResponse = paginationUtil.createPaginationResponse(
                permissionSummaryResponsePage
        );
        return responseUtil.createSuccessResponse(
                paginationResponse,
                "Retrieve paginated permissions successfully",
                HttpStatus.OK
        );
    }
}

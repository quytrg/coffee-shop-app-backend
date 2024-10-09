package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.supplier.SupplierCreateRequest;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.supplier.SupplierResponse;
import com.project.coffeeshopapp.services.supplier.SupplierService;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("${api.prefix}/suppliers")
public class SupplierController {
    private final SupplierService supplierService;
    private final ResponseUtil responseUtil;
    private final PaginationUtil paginationUtil;

    @PostMapping()
    public ResponseEntity<SuccessResponse<SupplierResponse>> createSupplier(
            @Valid @RequestBody SupplierCreateRequest supplierCreateRequest) {
        SupplierResponse supplierResponse = supplierService.createSupplier(supplierCreateRequest);
        return responseUtil.createSuccessResponse(
                supplierResponse,
                "Supplier created successfully",
                HttpStatus.CREATED
        );
    }
}

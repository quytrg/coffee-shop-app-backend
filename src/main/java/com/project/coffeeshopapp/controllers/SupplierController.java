package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.supplier.SupplierCreateRequest;
import com.project.coffeeshopapp.dtos.request.supplier.SupplierUpdateRequest;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.supplier.SupplierResponse;
import com.project.coffeeshopapp.dtos.response.supplier.SupplierResponse;
import com.project.coffeeshopapp.services.supplier.SupplierService;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<SupplierResponse>> updateSupplier(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody SupplierUpdateRequest supplierUpdateRequest) {
        SupplierResponse supplierResponse = supplierService.updateSupplier(id, supplierUpdateRequest);
        return responseUtil.createSuccessResponse(
                supplierResponse,
                "Supplier updated successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<SupplierResponse>> getSupplierById(
            @PathVariable(name = "id") Long id) {
        SupplierResponse supplierResponse = supplierService.getSupplier(id);
        return responseUtil.createSuccessResponse(
                supplierResponse,
                "Get supplier successfully",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> deleteSupplier(@PathVariable(name = "id") Long id) {
        supplierService.softDeleteSupplier(id);
        return responseUtil.createSuccessResponseWithoutData(
                "Supplier successfully deleted",
                HttpStatus.OK
        );
    }

}

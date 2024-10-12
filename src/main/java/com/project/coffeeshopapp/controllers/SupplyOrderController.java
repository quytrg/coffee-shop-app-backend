package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.supplyorder.SupplyOrderCreateRequest;
import com.project.coffeeshopapp.dtos.request.supplyorder.SupplyOrderUpdateRequest;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.supplyorder.SupplyOrderResponse;
import com.project.coffeeshopapp.services.supplyorder.SupplyOrderService;
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
@RequestMapping("${api.prefix}/supply-orders")
public class SupplyOrderController {
    private final SupplyOrderService supplyOrderService;
    private final ResponseUtil responseUtil;
    private final PaginationUtil paginationUtil;

    @PostMapping()
    public ResponseEntity<SuccessResponse<SupplyOrderResponse>> createSupplyOrder(
            @Valid @RequestBody SupplyOrderCreateRequest supplyOrderCreateRequest) {
        SupplyOrderResponse supplyOrderResponse = supplyOrderService.createSupplyOrder(supplyOrderCreateRequest);
        return responseUtil.createSuccessResponse(
                supplyOrderResponse,
                "Supply order was created successfully",
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<SupplyOrderResponse>> updateSupplyOrder(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody SupplyOrderUpdateRequest supplyOrderUpdateRequest) {
        SupplyOrderResponse supplyOrderResponse = supplyOrderService.updateSupplyOrder(id, supplyOrderUpdateRequest);
        return responseUtil.createSuccessResponse(
                supplyOrderResponse,
                "Supply order was updated successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<SupplyOrderResponse>> getSupplyOrderById(
            @PathVariable(name = "id") Long id) {
        SupplyOrderResponse supplyOrderResponse = supplyOrderService.getSupplyOrder(id);
        return responseUtil.createSuccessResponse(
                supplyOrderResponse,
                "Get supply order successfully",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> deleteSupplyOrder(
            @PathVariable(name = "id") Long id) {
        supplyOrderService.softDeleteSupplyOrder(id);
        return responseUtil.createSuccessResponseWithoutData(
                "Supply order was deleted successfully",
                HttpStatus.OK
        );
    }
}

package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.supplyorder.SupplyOrderCreateRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

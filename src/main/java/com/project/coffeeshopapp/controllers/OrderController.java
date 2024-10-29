package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.order.OrderCreateRequest;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.order.OrderResponse;
import com.project.coffeeshopapp.services.order.IOrderService;
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
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;
    private final ResponseUtil responseUtil;

    @PostMapping()
    public ResponseEntity<SuccessResponse<OrderResponse>> createOrder(
            @Valid @RequestBody OrderCreateRequest orderCreateRequest) {
        OrderResponse orderResponse = orderService.createOrder(orderCreateRequest);
        return responseUtil.createSuccessResponse(
                orderResponse,
                "Order was created successfully",
                HttpStatus.CREATED
        );
    }
}

package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.order.OrderCreateRequest;
import com.project.coffeeshopapp.dtos.request.order.OrderSearchRequest;
import com.project.coffeeshopapp.dtos.request.orderitem.OrderItemReportSearchRequest;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.order.OrderResponse;
import com.project.coffeeshopapp.dtos.response.order.OrderSummaryResponse;
import com.project.coffeeshopapp.dtos.response.orderitem.OrderItemReportResponse;
import com.project.coffeeshopapp.dtos.response.pagination.PaginationResponse;
import com.project.coffeeshopapp.services.order.IOrderService;
import com.project.coffeeshopapp.services.orderitem.IOrderItemService;
import com.project.coffeeshopapp.services.orderitem.OrderItemService;
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
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;
    private final ResponseUtil responseUtil;
    private final PaginationUtil paginationUtil;
    private final IOrderItemService orderItemService;

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

    @GetMapping
    public ResponseEntity<SuccessResponse<PaginationResponse<OrderSummaryResponse>>> getOrders(
            @Valid @ModelAttribute OrderSearchRequest request) {
        Page<OrderSummaryResponse> orderSummaryResponsePage = orderService.getOrders(request);
        PaginationResponse<OrderSummaryResponse> paginationResponse = paginationUtil.createPaginationResponse(
                orderSummaryResponsePage
        );
        return responseUtil.createSuccessResponse(
                paginationResponse,
                "Retrieve paginated orders successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<OrderResponse>> getOrderById(
            @PathVariable(name = "id") Long id) {
        OrderResponse orderResponse = orderService.getOrder(id);
        return responseUtil.createSuccessResponse(
                orderResponse,
                "Get order successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/order-items/report")
    public ResponseEntity<SuccessResponse<PaginationResponse<OrderItemReportResponse>>> getOrderItemsReport(
            @Valid @ModelAttribute OrderItemReportSearchRequest request) {
        Page<OrderItemReportResponse> orderItemReportResponsePage = orderItemService.getOrderItemsReport(request);
        PaginationResponse<OrderItemReportResponse> paginationResponse = paginationUtil.createPaginationResponse(
                orderItemReportResponsePage
        );
        return responseUtil.createSuccessResponse(
                paginationResponse,
                "Retrieve paginated order items report successfully",
                HttpStatus.OK
        );
    }
}

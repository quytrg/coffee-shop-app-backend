package com.project.coffeeshopapp.services.order;

import com.project.coffeeshopapp.dtos.request.order.OrderCreateRequest;
import com.project.coffeeshopapp.dtos.request.order.OrderSearchRequest;
import com.project.coffeeshopapp.dtos.response.order.OrderResponse;
import com.project.coffeeshopapp.dtos.response.order.OrderSummaryResponse;
import org.springframework.data.domain.Page;

public interface IOrderService {
    OrderResponse createOrder(OrderCreateRequest orderCreateRequest);
    Page<OrderSummaryResponse> getOrders(OrderSearchRequest orderSearchRequest);
    OrderResponse getOrder(Long orderId);
}

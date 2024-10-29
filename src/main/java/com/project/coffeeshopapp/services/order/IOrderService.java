package com.project.coffeeshopapp.services.order;

import com.project.coffeeshopapp.dtos.request.order.OrderCreateRequest;
import com.project.coffeeshopapp.dtos.response.order.OrderResponse;

public interface IOrderService {
    OrderResponse createOrder(OrderCreateRequest orderCreateRequest);
}

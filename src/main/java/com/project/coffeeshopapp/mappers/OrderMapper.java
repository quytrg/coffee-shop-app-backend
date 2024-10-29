package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.request.order.OrderCreateRequest;
import com.project.coffeeshopapp.dtos.response.order.OrderResponse;
import com.project.coffeeshopapp.enums.OrderStatus;
import com.project.coffeeshopapp.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = { OrderStatus.class }, uses = { OrderItemMapper.class })
public interface OrderMapper {
    @Mapping(target = "orderCode", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "returnAmount", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", expression = ("java(OrderStatus.COMPLETED)"))
    Order orderCreateRequestToOrder(OrderCreateRequest orderCreateRequest);

    @Mapping(source = "orderItems", target = "orderItems")
    OrderResponse orderToOrderResponse(Order order);
}

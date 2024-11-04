package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.request.orderitem.OrderItemCreateRequest;
import com.project.coffeeshopapp.dtos.response.orderitem.OrderItemResponse;
import com.project.coffeeshopapp.models.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "productVariant", ignore = true)
    @Mapping(target = "discount", expression = ("java(0)"))
    OrderItem orderItemCreateRequestToOrderItem(OrderItemCreateRequest orderItemCreateRequest);

    @Mapping(source = "productVariant.id", target = "productVariantId")
    @Mapping(source = "productVariant.product.name", target = "productName")
    @Mapping(source = "productVariant.size", target = "productVariantSize")
    OrderItemResponse orderItemToOrderItemResponse(OrderItem orderItem);

    List<OrderItemResponse> orderItemsToOrderItemResponses(List<OrderItem> orderItems);
}

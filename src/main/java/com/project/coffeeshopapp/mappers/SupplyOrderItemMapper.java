package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.constants.AppConstants;
import com.project.coffeeshopapp.dtos.request.supplyorderitem.SupplyOrderItemRequest;
import com.project.coffeeshopapp.dtos.response.supplyorderitem.SupplyOrderItemResponse;
import com.project.coffeeshopapp.models.SupplyOrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplyOrderItemMapper {
    @Mapping(target = "expirationDate", source = "expirationDate", dateFormat = AppConstants.DATE_FORMAT)
    @Mapping(target = "supplyOrder", ignore = true)
    @Mapping(target = "ingredient", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    SupplyOrderItem supplyOrderItemRequestToSupplyOrderItem(SupplyOrderItemRequest supplyOrderItemRequest);

    @Mapping(target = "expirationDate", source = "expirationDate", dateFormat = AppConstants.DATE_FORMAT)
    @Mapping(source = "ingredient.id", target = "ingredientId")
    @Mapping(source = "ingredient.name", target = "ingredientName")
    SupplyOrderItemResponse supplyOrderItemToSupplyOrderItemResponse(SupplyOrderItem supplyOrderItem);

    List<SupplyOrderItemResponse> supplyOrderItemsToSupplyOrderItemResponses(List<SupplyOrderItem> supplyOrderItems);
}

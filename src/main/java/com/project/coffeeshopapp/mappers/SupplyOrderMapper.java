package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.constants.AppConstants;
import com.project.coffeeshopapp.dtos.request.supplyorder.SupplyOrderCreateRequest;
import com.project.coffeeshopapp.dtos.response.supplyorder.SupplyOrderResponse;
import com.project.coffeeshopapp.models.SupplyOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { SupplyOrderItemMapper.class, SupplierMapper.class })
public interface SupplyOrderMapper {
    @Mapping(target = "expectedDeliveryDate", source = "expectedDeliveryDate", dateFormat = AppConstants.DATE_FORMAT)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "supplyOrderItems", ignore = true)
    @Mapping(target = "actualDeliveryDate", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "orderCode", ignore = true)
    SupplyOrder supplyOrderCreateRequestToSupplyOrder(SupplyOrderCreateRequest supplyOrderCreateRequest);

    @Mapping(target = "expectedDeliveryDate", source = "expectedDeliveryDate", dateFormat = AppConstants.DATE_FORMAT)
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = AppConstants.DATETIME_FORMAT)
    @Mapping(target = "updatedAt", source = "updatedAt", dateFormat = AppConstants.DATETIME_FORMAT)
    @Mapping(source = "supplyOrderItems", target = "supplyOrderItems")
    @Mapping(source = "supplier", target = "supplier")
    SupplyOrderResponse supplyOrderToSupplyOrderResponse(SupplyOrder supplyOrder);
}

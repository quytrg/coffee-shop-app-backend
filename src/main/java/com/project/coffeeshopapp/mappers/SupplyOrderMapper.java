package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.request.supplyorder.SupplyOrderCreateRequest;
import com.project.coffeeshopapp.dtos.request.supplyorder.SupplyOrderUpdateRequest;
import com.project.coffeeshopapp.dtos.response.supplyorder.SupplyOrderResponse;
import com.project.coffeeshopapp.dtos.response.supplyorder.SupplyOrderSummaryResponse;
import com.project.coffeeshopapp.models.SupplyOrder;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { SupplyOrderItemMapper.class, SupplierMapper.class })
public interface SupplyOrderMapper {
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "supplyOrderItems", ignore = true)
    @Mapping(target = "actualDeliveryDate", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "orderCode", ignore = true)
    SupplyOrder supplyOrderCreateRequestToSupplyOrder(SupplyOrderCreateRequest supplyOrderCreateRequest);

    @Mapping(source = "supplyOrderItems", target = "supplyOrderItems")
    @Mapping(source = "supplier", target = "supplier")
    SupplyOrderResponse supplyOrderToSupplyOrderResponse(SupplyOrder supplyOrder);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "supplyOrderItems", ignore = true)
    @Mapping(target = "actualDeliveryDate", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "orderCode", ignore = true)
    void supplyOrderUpdateRequestToSupplyOrder(
            SupplyOrderUpdateRequest supplyOrderUpdateRequest,
            @MappingTarget SupplyOrder supplyOrder
    );

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    SupplyOrderSummaryResponse supplyOrderToSupplyOrderSummaryResponse(SupplyOrder supplyOrder);
}

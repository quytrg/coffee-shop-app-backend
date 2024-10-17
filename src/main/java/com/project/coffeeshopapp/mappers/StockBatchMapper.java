package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.response.stockbatch.StockBatchResponse;
import com.project.coffeeshopapp.models.StockBatch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockBatchMapper {
    @Mapping(source = "ingredient.id", target = "ingredientId")
    @Mapping(source = "ingredient.name", target = "ingredientName")
    @Mapping(source = "supplyOrderItem.quantity", target = "numberOfItems")
    @Mapping(source = "supplyOrderItem.price", target = "pricePerItem")
    @Mapping(source = "supplyOrderItem.unitValue", target = "unitValue")
    @Mapping(source = "ingredient.defaultUnit", target = "defaultUnit")
    @Mapping(source = "supplyOrderItem.subtotal", target = "subtotal")
    @Mapping(source = "supplyOrderItem.supplyOrder.id", target = "supplyOrderId")
    @Mapping(source = "supplyOrderItem.supplyOrder.orderCode", target = "supplyOrderCode")
    @Mapping(source = "supplyOrderItem.supplyOrder.description", target = "supplyOrderDescription")
    @Mapping(source = "supplyOrderItem.supplyOrder.actualDeliveryDate", target = "receivedDate")
    @Mapping(source = "supplyOrderItem.expirationDate", target = "expirationDate")
    @Mapping(source = "supplyOrderItem.supplyOrder.supplier.id", target = "supplierId")
    @Mapping(source = "supplyOrderItem.supplyOrder.supplier.name", target = "supplierName")
    StockBatchResponse stockBatchToStockBatchResponse(StockBatch stockBatch);
}

package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.projection.StockBatchSummary;
import com.project.coffeeshopapp.dtos.request.stockbatch.StockBatchUpdateRequest;
import com.project.coffeeshopapp.dtos.response.stockbatch.StockBatchResponse;
import com.project.coffeeshopapp.dtos.response.stockbatch.StockBatchSummaryResponse;
import com.project.coffeeshopapp.models.StockBatch;
import org.mapstruct.*;

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

    @Mapping(source = "ingredient.id", target = "ingredientId")
    @Mapping(source = "ingredient.name", target = "ingredientName")
    @Mapping(source = "ingredient.defaultUnit", target = "defaultUnit")
    @Mapping(source = "supplyOrderItem.supplyOrder.id", target = "supplyOrderId")
    @Mapping(source = "supplyOrderItem.supplyOrder.orderCode", target = "supplyOrderCode")
    @Mapping(source = "supplyOrderItem.supplyOrder.actualDeliveryDate", target = "receivedDate")
    @Mapping(source = "supplyOrderItem.expirationDate", target = "expirationDate")
    @Mapping(source = "supplyOrderItem.supplyOrder.supplier.id", target = "supplierId")
    @Mapping(source = "supplyOrderItem.supplyOrder.supplier.name", target = "supplierName")
    StockBatchSummaryResponse stockBatchToStockBatchSummaryResponse(StockBatch stockBatch);

    StockBatchSummaryResponse stockBatchSummaryToStockBatchSummaryResponse(StockBatchSummary stockBatchSummary);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "ingredient", ignore = true)
    @Mapping(target = "supplyOrderItem", ignore = true)
    void stockBatchUpdateRequestToStockBatch(
            StockBatchUpdateRequest stockBatchUpdateRequest,
            @MappingTarget StockBatch stockBatch
    );
}

package com.project.coffeeshopapp.dtos.projection;

import com.project.coffeeshopapp.enums.MeasurementUnit;
import com.project.coffeeshopapp.models.StockBatch;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StockBatchSummary {
    private Long id;
    private Long ingredientId;
    private String ingredientName;
    private MeasurementUnit defaultUnit;
    private BigDecimal initialQuantity;
    private BigDecimal remainingQuantity;
    private Long supplyOrderId;
    private String supplyOrderCode;
    private LocalDateTime receivedDate;
    private LocalDateTime expirationDate;
    private Long supplierId;
    private String supplierName;
}

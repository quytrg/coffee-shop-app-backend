package com.project.coffeeshopapp.dtos.projection;

import com.project.coffeeshopapp.enums.MeasurementUnit;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StockBatchReport {
    private Long id;
    private Long ingredientId;
    private String ingredientName;
    private Integer numberOfItems;
    private BigDecimal pricePerItem;
    private BigDecimal unitValue;
    private MeasurementUnit defaultUnit;
    private BigDecimal subtotal;
    private BigDecimal initialQuantity;
    private BigDecimal remainingQuantity;
    private Long supplyOrderId;
    private String supplyOrderCode;
    private String supplyOrderDescription;
    private LocalDateTime receivedDate;
    private LocalDateTime expirationDate;
    private Long supplierId;
    private String supplierName;
}

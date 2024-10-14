package com.project.coffeeshopapp.dtos.response.supplyorder;

import com.project.coffeeshopapp.enums.PaymentStatus;
import com.project.coffeeshopapp.enums.SupplyOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyOrderSummaryResponse {
    private Long id;
    private String orderCode;
    private String description;
    private SupplyOrderStatus status;
    private LocalDateTime expectedDeliveryDate;
    private LocalDateTime actualDeliveryDate;
    private PaymentStatus paymentStatus;
    private BigDecimal totalAmount;
    private String updatedBy;
    private LocalDateTime updatedAt;
    private String createdBy;
    private LocalDateTime createdAt;
    private Long supplierId;
    private String supplierName;
}

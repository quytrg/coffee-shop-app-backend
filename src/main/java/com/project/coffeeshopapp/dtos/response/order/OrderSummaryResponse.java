package com.project.coffeeshopapp.dtos.response.order;

import com.project.coffeeshopapp.enums.OrderStatus;
import com.project.coffeeshopapp.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummaryResponse {
    private Long id;
    private String orderCode;
    private String customerName;
    private BigDecimal totalAmount;
    private PaymentMethod paymentMethod;
    private OrderStatus status;
    private BigDecimal receivedAmount;
    private BigDecimal returnAmount;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}

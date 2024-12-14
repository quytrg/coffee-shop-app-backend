package com.project.coffeeshopapp.dtos.response.order;

import com.project.coffeeshopapp.dtos.response.orderitem.OrderItemResponse;
import com.project.coffeeshopapp.enums.OrderStatus;
import com.project.coffeeshopapp.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderCode;
    private String customerName;
    private String description;
    private BigDecimal totalAmount;
    private PaymentMethod paymentMethod;
    private OrderStatus status;
    private BigDecimal receivedAmount;
    private BigDecimal returnAmount;
    private List<OrderItemResponse> orderItems = new ArrayList<>();
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}

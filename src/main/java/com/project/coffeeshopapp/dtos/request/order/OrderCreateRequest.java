package com.project.coffeeshopapp.dtos.request.order;

import com.project.coffeeshopapp.dtos.request.orderitem.OrderItemCreateRequest;
import com.project.coffeeshopapp.dtos.request.supplyorderitem.SupplyOrderItemRequest;
import com.project.coffeeshopapp.enums.OrderStatus;
import com.project.coffeeshopapp.enums.PaymentMethod;
import com.project.coffeeshopapp.models.OrderItem;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequest {
    private String customerName;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @NotNull(message = "Received amount is mandatory")
    @PositiveOrZero(message = "Received amount must be non-negative value")
    @DecimalMax(value = "999999999.99", message = "Received amount cannot exceed 999,999,999.99")
    private BigDecimal receivedAmount;

    @NotEmpty(message = "Order items cannot be empty")
    @Size(min = 1, message = "Order items must contain at least one value")
    private List<OrderItemCreateRequest> orderItems = new ArrayList<>();
}

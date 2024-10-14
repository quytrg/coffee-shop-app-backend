package com.project.coffeeshopapp.dtos.request.supplyorder;

import com.project.coffeeshopapp.dtos.request.supplyorderitem.SupplyOrderItemRequest;
import com.project.coffeeshopapp.enums.PaymentMethod;
import com.project.coffeeshopapp.enums.PaymentStatus;
import com.project.coffeeshopapp.enums.SupplyOrderStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyOrderCreateRequest {
    @NotNull(message = "Supply order status is required")
    private SupplyOrderStatus status;

    private LocalDateTime expectedDeliveryDate;

    private PaymentStatus paymentStatus;

    private PaymentMethod paymentMethod;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @NotNull(message = "Supplier ID is required")
    private Long supplierId;

    @NotEmpty(message = "Supply order items cannot be empty")
    @Size(min = 1, message = "Supply order items must contain at least one value")
    private List<SupplyOrderItemRequest> supplyOrderItems = new ArrayList<>();
}

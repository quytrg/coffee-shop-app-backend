package com.project.coffeeshopapp.dtos.request.supplyorder;

import com.project.coffeeshopapp.dtos.request.supplyorderitem.SupplyOrderItemRequest;
import com.project.coffeeshopapp.enums.PaymentMethod;
import com.project.coffeeshopapp.enums.PaymentStatus;
import com.project.coffeeshopapp.enums.SupplyOrderStatus;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyOrderUpdateRequest {
    private SupplyOrderStatus status;
    private LocalDateTime expectedDeliveryDate;
    private LocalDateTime actualDeliveryDate;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;
    private Long supplierId;
    private List<SupplyOrderItemRequest> supplyOrderItems;
}

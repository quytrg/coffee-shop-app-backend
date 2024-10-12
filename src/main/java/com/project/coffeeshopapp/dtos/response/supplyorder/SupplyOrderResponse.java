package com.project.coffeeshopapp.dtos.response.supplyorder;

import com.project.coffeeshopapp.dtos.request.supplyorderitem.SupplyOrderItemRequest;
import com.project.coffeeshopapp.dtos.response.supplier.SupplierSummaryResponse;
import com.project.coffeeshopapp.dtos.response.supplyorderitem.SupplyOrderItemResponse;
import com.project.coffeeshopapp.enums.PaymentMethod;
import com.project.coffeeshopapp.enums.PaymentStatus;
import com.project.coffeeshopapp.enums.SupplyOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyOrderResponse {
    private Long id;
    private String orderCode;
    private SupplyOrderStatus status;
    private String expectedDeliveryDate;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private String description;
    private SupplierSummaryResponse supplier;
    private List<SupplyOrderItemResponse> supplyOrderItems = new ArrayList<>();
    private BigDecimal totalAmount;
    private String createdBy;
    private String createdAt;
    private String updatedBy;
    private String updatedAt;
}

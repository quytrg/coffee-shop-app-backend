package com.project.coffeeshopapp.services.supplyorder;

import com.project.coffeeshopapp.dtos.request.supplyorder.SupplyOrderCreateRequest;
import com.project.coffeeshopapp.dtos.request.supplyorder.SupplyOrderSearchRequest;
import com.project.coffeeshopapp.dtos.request.supplyorder.SupplyOrderUpdateRequest;
import com.project.coffeeshopapp.dtos.response.supplyorder.SupplyOrderResponse;
import com.project.coffeeshopapp.dtos.response.supplyorder.SupplyOrderSummaryResponse;
import org.springframework.data.domain.Page;

public interface ISupplyOrderService {
    SupplyOrderResponse createSupplyOrder(SupplyOrderCreateRequest supplyOrderCreateRequest);
    SupplyOrderResponse updateSupplyOrder(Long id, SupplyOrderUpdateRequest supplyOrderUpdateRequest);
    SupplyOrderResponse getSupplyOrder(Long id);
    void softDeleteSupplyOrder(Long id);
    Page<SupplyOrderSummaryResponse> getSupplyOrders(SupplyOrderSearchRequest supplyOrderSearchRequest);
}

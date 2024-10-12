package com.project.coffeeshopapp.services.supplyorder;

import com.project.coffeeshopapp.dtos.request.supplyorder.SupplyOrderCreateRequest;
import com.project.coffeeshopapp.dtos.response.supplyorder.SupplyOrderResponse;

public interface ISupplyOrderService {
    SupplyOrderResponse createSupplyOrder(SupplyOrderCreateRequest supplyOrderCreateRequest);
}

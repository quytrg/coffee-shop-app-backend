package com.project.coffeeshopapp.services.orderitem;

import com.project.coffeeshopapp.dtos.request.orderitem.OrderItemReportSearchRequest;
import com.project.coffeeshopapp.dtos.response.orderitem.OrderItemReportResponse;
import org.springframework.data.domain.Page;

public interface IOrderItemService {
    Page<OrderItemReportResponse> getOrderItemsReport(OrderItemReportSearchRequest orderItemReportSearchRequest);
}

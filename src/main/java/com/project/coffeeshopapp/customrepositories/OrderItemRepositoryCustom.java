package com.project.coffeeshopapp.customrepositories;

import com.project.coffeeshopapp.dtos.projection.OrderItemReport;
import com.project.coffeeshopapp.dtos.request.orderitem.OrderItemReportSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderItemRepositoryCustom {
    Page<OrderItemReport> findAllForReport(
            OrderItemReportSearchRequest request, Pageable pageable
    );
}

package com.project.coffeeshopapp.services.orderitem;

import com.project.coffeeshopapp.dtos.projection.OrderItemReport;
import com.project.coffeeshopapp.dtos.request.orderitem.OrderItemReportSearchRequest;
import com.project.coffeeshopapp.dtos.response.orderitem.OrderItemReportResponse;
import com.project.coffeeshopapp.mappers.OrderItemMapper;
import com.project.coffeeshopapp.repositories.OrderItemRepository;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderItemService implements IOrderItemService {
    private final PaginationUtil paginationUtil;
    private final SortUtil sortUtil;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<OrderItemReportResponse> getOrderItemsReport(OrderItemReportSearchRequest orderItemReportSearchRequest) {
        Sort sort = sortUtil.createSort(
                orderItemReportSearchRequest.getSortBy(),
                orderItemReportSearchRequest.getSortDir()
        );
        Pageable pageable = paginationUtil.createPageable(
                orderItemReportSearchRequest.getPage(),
                orderItemReportSearchRequest.getSize(),
                sort
        );

        Page<OrderItemReport> orderItemReports = orderItemRepository.findAllForReport(
                orderItemReportSearchRequest,
                pageable
        );
        return orderItemReports.map(orderItemMapper::orderItemReportToOrderItemReportResponse);
    }
}

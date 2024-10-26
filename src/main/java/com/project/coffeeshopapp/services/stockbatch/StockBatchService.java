package com.project.coffeeshopapp.services.stockbatch;

import com.project.coffeeshopapp.customexceptions.DataNotFoundException;
import com.project.coffeeshopapp.customexceptions.InvalidDataException;
import com.project.coffeeshopapp.dtos.projection.StockBatchReport;
import com.project.coffeeshopapp.dtos.projection.StockBatchSummary;
import com.project.coffeeshopapp.dtos.request.stockbatch.StockBatchReportSearchRequest;
import com.project.coffeeshopapp.dtos.request.stockbatch.StockBatchSearchRequest;
import com.project.coffeeshopapp.dtos.request.stockbatch.StockBatchUpdateRequest;
import com.project.coffeeshopapp.dtos.response.stockbatch.StockBatchReportResponse;
import com.project.coffeeshopapp.dtos.response.stockbatch.StockBatchResponse;
import com.project.coffeeshopapp.dtos.response.stockbatch.StockBatchSummaryResponse;
import com.project.coffeeshopapp.mappers.StockBatchMapper;
import com.project.coffeeshopapp.models.StockBatch;
import com.project.coffeeshopapp.repositories.StockBatchRepository;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockBatchService implements IStockBatchService {
    private final StockBatchRepository stockBatchRepository;
    private final StockBatchMapper stockBatchMapper;
    private final PaginationUtil paginationUtil;
    private final SortUtil sortUtil;

    @Override
    @Transactional(readOnly = true)
    public StockBatchResponse getStockBatchById(Long id) {
        // check if StockBatch ID exists
        StockBatch stockBatch = stockBatchRepository.findByIdWithIngredientAndSupplyOrderItem(id)
                .orElseThrow(() -> new DataNotFoundException("StockBatch", "StockBatch not found with ID: " + id));
        return stockBatchMapper.stockBatchToStockBatchResponse(stockBatch);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockBatchSummaryResponse> getStockBatches(StockBatchSearchRequest stockBatchSearchRequest) {
        Sort sort = sortUtil.createSort(
                stockBatchSearchRequest.getSortBy(),
                stockBatchSearchRequest.getSortDir()
        );
        Pageable pageable = paginationUtil.createPageable(
                stockBatchSearchRequest.getPage(),
                stockBatchSearchRequest.getSize(),
                sort
        );
        Page<StockBatchSummary> stockBatchSummaries = stockBatchRepository.findAllWithFiltersAndSort(
                stockBatchSearchRequest,
                pageable
        );
        return stockBatchSummaries.map(stockBatchMapper::stockBatchSummaryToStockBatchSummaryResponse);
    }

    @Override
    @Transactional
    public StockBatchResponse updateStockBatch(Long id, StockBatchUpdateRequest stockBatchUpdateRequest) {
        // check if StockBatch ID exists
        StockBatch stockBatch = stockBatchRepository.findByIdWithIngredientAndSupplyOrderItem(id)
                .orElseThrow(() -> new DataNotFoundException("StockBatch", "StockBatch not found with ID: " + id));

        stockBatchMapper.stockBatchUpdateRequestToStockBatch(
                stockBatchUpdateRequest,
                stockBatch
        );

        // update expirationDate
        Optional.ofNullable(stockBatchUpdateRequest.getExpirationDate())
                .ifPresent(newExpirationDate -> {
                    // check business logic: expirationDate must be after receivedDate
                    if (newExpirationDate.isBefore(stockBatch.getSupplyOrderItem().getSupplyOrder().getActualDeliveryDate())) {
                        throw new InvalidDataException("expirationDate", "Expiration date cannot be before received date");
                    }
                    stockBatch.getSupplyOrderItem().setExpirationDate(newExpirationDate);
                });

        StockBatch updatedStockBatch = stockBatchRepository.save(stockBatch);
        return stockBatchMapper.stockBatchToStockBatchResponse(updatedStockBatch);
    }

    @Override
    @Transactional
    public void softDeleteStockBatch(Long id) {
        // check if StockBatch ID exists
        stockBatchRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("StockBatch", "StockBatch not found with ID: " + id));
        stockBatchRepository.softDelete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockBatchReportResponse> getStockBatchReport(StockBatchReportSearchRequest request) {
        Sort sort = sortUtil.createSort(
                request.getSortBy(),
                request.getSortDir()
        );
        Pageable pageable = paginationUtil.createPageable(
                request.getPage(),
                request.getSize(),
                sort
        );
        Page<StockBatchReport> stockBatchReports = stockBatchRepository.findAllForReport(request, pageable);
        return stockBatchReports.map(stockBatchMapper::stockBatchReportToStockBatchReportResponse);
    }
}

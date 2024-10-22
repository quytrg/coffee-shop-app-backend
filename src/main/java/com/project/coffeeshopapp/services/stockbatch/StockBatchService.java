package com.project.coffeeshopapp.services.stockbatch;

import com.project.coffeeshopapp.customexceptions.DataNotFoundException;
import com.project.coffeeshopapp.dtos.projection.StockBatchSummary;
import com.project.coffeeshopapp.dtos.request.stockbatch.StockBatchSearchRequest;
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
}

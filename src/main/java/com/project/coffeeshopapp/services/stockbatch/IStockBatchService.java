package com.project.coffeeshopapp.services.stockbatch;

import com.project.coffeeshopapp.dtos.request.stockbatch.StockBatchSearchRequest;
import com.project.coffeeshopapp.dtos.response.stockbatch.StockBatchResponse;
import com.project.coffeeshopapp.dtos.response.stockbatch.StockBatchSummaryResponse;
import org.springframework.data.domain.Page;

public interface IStockBatchService {
    StockBatchResponse getStockBatchById(Long id);
    Page<StockBatchSummaryResponse> getStockBatches(StockBatchSearchRequest stockBatchSearchRequest);
}

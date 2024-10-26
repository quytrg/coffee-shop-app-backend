package com.project.coffeeshopapp.services.stockbatch;

import com.project.coffeeshopapp.dtos.request.stockbatch.StockBatchReportSearchRequest;
import com.project.coffeeshopapp.dtos.request.stockbatch.StockBatchSearchRequest;
import com.project.coffeeshopapp.dtos.request.stockbatch.StockBatchUpdateRequest;
import com.project.coffeeshopapp.dtos.response.stockbatch.StockBatchReportResponse;
import com.project.coffeeshopapp.dtos.response.stockbatch.StockBatchResponse;
import com.project.coffeeshopapp.dtos.response.stockbatch.StockBatchSummaryResponse;
import org.springframework.data.domain.Page;

public interface IStockBatchService {
    StockBatchResponse getStockBatchById(Long id);
    Page<StockBatchSummaryResponse> getStockBatches(StockBatchSearchRequest stockBatchSearchRequest);
    StockBatchResponse updateStockBatch(Long id, StockBatchUpdateRequest stockBatchUpdateRequest);
    void softDeleteStockBatch(Long id);
    Page<StockBatchReportResponse> getStockBatchReport(StockBatchReportSearchRequest stockBatchReportSearchRequest);
}

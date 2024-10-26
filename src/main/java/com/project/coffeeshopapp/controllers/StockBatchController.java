package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.stockbatch.StockBatchReportSearchRequest;
import com.project.coffeeshopapp.dtos.request.stockbatch.StockBatchSearchRequest;
import com.project.coffeeshopapp.dtos.request.stockbatch.StockBatchUpdateRequest;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.pagination.PaginationResponse;
import com.project.coffeeshopapp.dtos.response.stockbatch.StockBatchReportResponse;
import com.project.coffeeshopapp.dtos.response.stockbatch.StockBatchResponse;
import com.project.coffeeshopapp.dtos.response.stockbatch.StockBatchSummaryResponse;
import com.project.coffeeshopapp.services.stockbatch.IStockBatchService;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("${api.prefix}/stock-batches")
public class StockBatchController {
    private final IStockBatchService stockBatchService;
    private final ResponseUtil responseUtil;
    private final PaginationUtil paginationUtil;

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<StockBatchResponse>> getStockBatchById(
            @PathVariable(name = "id") Long id) {
        StockBatchResponse stockbatchResponse = stockBatchService.getStockBatchById(id);
        return responseUtil.createSuccessResponse(
                stockbatchResponse,
                "Get stock batch successfully",
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<PaginationResponse<StockBatchSummaryResponse>>> getStockBatches(
            @Valid @ModelAttribute StockBatchSearchRequest request) {
        Page<StockBatchSummaryResponse> stockbatchSummaryResponsePage = stockBatchService.getStockBatches(request);
        PaginationResponse<StockBatchSummaryResponse> paginationResponse = paginationUtil.createPaginationResponse(
                stockbatchSummaryResponsePage
        );
        return responseUtil.createSuccessResponse(
                paginationResponse,
                "Retrieve paginated stock batches successfully",
                HttpStatus.OK
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<StockBatchResponse>> updateStockBatch(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody StockBatchUpdateRequest stockbatchUpdateRequest) {
        StockBatchResponse stockbatchResponse = stockBatchService.updateStockBatch(id, stockbatchUpdateRequest);
        return responseUtil.createSuccessResponse(
                stockbatchResponse,
                "StockBatch updated successfully",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> deleteStockBatch(@PathVariable(name = "id") Long id) {
        stockBatchService.softDeleteStockBatch(id);
        return responseUtil.createSuccessResponseWithoutData(
                "StockBatch deleted successfully",
                HttpStatus.OK
        );
    }

    // Endpoint cho báo cáo
    @GetMapping("/report")
    public ResponseEntity<SuccessResponse<PaginationResponse<StockBatchReportResponse>>> getStockBatchReport(
            @Valid @ModelAttribute StockBatchReportSearchRequest request) {
        Page<StockBatchReportResponse> stockBatchReportPage = stockBatchService.getStockBatchReport(request);
        PaginationResponse<StockBatchReportResponse> paginationResponse = paginationUtil.createPaginationResponse(
                stockBatchReportPage
        );
        return responseUtil.createSuccessResponse(
                paginationResponse,
                "Retrieve stock batch report successfully",
                HttpStatus.OK
        );
    }
}

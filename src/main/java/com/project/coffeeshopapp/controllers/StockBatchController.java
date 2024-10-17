package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.stockbatch.StockBatchResponse;
import com.project.coffeeshopapp.services.stockbatch.IStockBatchService;
import com.project.coffeeshopapp.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("${api.prefix}/stock-batches")
public class StockBatchController {
    private final IStockBatchService stockBatchService;
    private final ResponseUtil responseUtil;

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
}

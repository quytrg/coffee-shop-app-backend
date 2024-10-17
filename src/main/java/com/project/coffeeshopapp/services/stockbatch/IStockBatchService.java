package com.project.coffeeshopapp.services.stockbatch;

import com.project.coffeeshopapp.dtos.response.stockbatch.StockBatchResponse;

public interface IStockBatchService {
    StockBatchResponse getStockBatchById(Long id);
}

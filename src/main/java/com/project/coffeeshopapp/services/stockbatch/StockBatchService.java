package com.project.coffeeshopapp.services.stockbatch;

import com.project.coffeeshopapp.customexceptions.DataNotFoundException;
import com.project.coffeeshopapp.dtos.response.stockbatch.StockBatchResponse;
import com.project.coffeeshopapp.mappers.StockBatchMapper;
import com.project.coffeeshopapp.models.StockBatch;
import com.project.coffeeshopapp.repositories.StockBatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockBatchService implements IStockBatchService {
    private final StockBatchRepository stockBatchRepository;
    private final StockBatchMapper stockBatchMapper;

    @Override
    @Transactional(readOnly = true)
    public StockBatchResponse getStockBatchById(Long id) {
        // check if StockBatch ID exists
        StockBatch stockBatch = stockBatchRepository.findByIdWithIngredientAndSupplyOrderItem(id)
                .orElseThrow(() -> new DataNotFoundException("StockBatch", "StockBatch not found with ID: " + id));
        return stockBatchMapper.stockBatchToStockBatchResponse(stockBatch);
    }
}

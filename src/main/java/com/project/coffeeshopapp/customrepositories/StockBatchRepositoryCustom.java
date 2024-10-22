package com.project.coffeeshopapp.customrepositories;

import com.project.coffeeshopapp.dtos.projection.StockBatchSummary;
import com.project.coffeeshopapp.dtos.request.stockbatch.StockBatchSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StockBatchRepositoryCustom {
    Page<StockBatchSummary> findAllWithFiltersAndSort(
            StockBatchSearchRequest stockBatchSearchRequest,
            Pageable pageable
    );
}

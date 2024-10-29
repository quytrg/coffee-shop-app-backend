package com.project.coffeeshopapp.services.stockbatch;

import com.project.coffeeshopapp.customexceptions.DataNotFoundException;
import com.project.coffeeshopapp.customexceptions.InvalidDataException;
import com.project.coffeeshopapp.dtos.projection.StockBatchReport;
import com.project.coffeeshopapp.dtos.projection.StockBatchSummary;
import com.project.coffeeshopapp.dtos.request.stockbatch.StockBatchDeductRequest;
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

import java.math.BigDecimal;
import java.util.List;
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


    /**
     * Deducts the specified quantity of an ingredient from available StockBatches.
     *
     * @param request The request containing ingredientId and quantity to deduct.
     */
    @Override
    @Transactional
    public void deductStockBatch(StockBatchDeductRequest request) {
        Long ingredientId = request.getIngredientId();
        BigDecimal quantityToDeduct = request.getQuantity();

        if (quantityToDeduct.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidDataException("quantity", "Quantity to deduct must be positive");
        }

        // Fetch StockBatches ordered by expirationDate ascending, then remainingQuantity ascending
        List<StockBatch> stockBatches = stockBatchRepository
                .findAvailableStockBatchesByIngredientIdOrderByExpirationDateAscRemainingQuantityAsc(ingredientId);

        if (stockBatches.isEmpty()) {
            throw new DataNotFoundException("StockBatch", "No StockBatch found for Ingredient ID: " + ingredientId);
        }

        BigDecimal remainingToDeduct = quantityToDeduct;

        for (StockBatch stockBatch : stockBatches) {
            if (stockBatch.getRemainingQuantity().compareTo(BigDecimal.ZERO) <= 0) {
                continue; // Skip if no remaining quantity
            }

            BigDecimal availableQuantity = stockBatch.getRemainingQuantity();

            if (availableQuantity.compareTo(remainingToDeduct) >= 0) {
                // StockBatch can fulfill the remaining deduction
                stockBatch.setRemainingQuantity(availableQuantity.subtract(remainingToDeduct));
                stockBatchRepository.save(stockBatch);
                remainingToDeduct = BigDecimal.ZERO;
                break;
            } else {
                // Deduct all available quantity from this StockBatch and continue with the next StockBatch
                stockBatch.setRemainingQuantity(BigDecimal.ZERO);
                stockBatchRepository.save(stockBatch);
                remainingToDeduct = remainingToDeduct.subtract(availableQuantity);
            }
        }

        if (remainingToDeduct.compareTo(BigDecimal.ZERO) > 0) {
            throw new InvalidDataException("remainingQuantity", "Insufficient stock for Ingredient ID: " + ingredientId);
        }
    }
}

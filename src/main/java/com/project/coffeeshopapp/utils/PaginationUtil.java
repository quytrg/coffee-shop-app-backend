package com.project.coffeeshopapp.utils;

import com.project.coffeeshopapp.dtos.response.pagination.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PaginationUtil {

    public <T> PaginationResponse<T> createPaginationResponse(Page<T> page, int number, int size) {
        return PaginationResponse.<T>builder()
                .content(page.getContent())
                .page(createPageInfo(page.getTotalElements(), page.getTotalPages(), number, size))
                .build();
    }

    private PaginationResponse.PageInfo createPageInfo(long totalElements, int totalPages, int number, int size) {
        return PaginationResponse.PageInfo.builder()
                .number(number)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }
}


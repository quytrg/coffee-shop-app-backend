package com.project.coffeeshopapp.utils;

import com.project.coffeeshopapp.dtos.response.pagination.PaginationResponse;
import com.project.coffeeshopapp.properties.PaginationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaginationUtil {
    private final PaginationProperties paginationProperties;

    public <T> PaginationResponse<T> createPaginationResponse(Page<T> page) {
        return PaginationResponse.<T>builder()
                .content(page.getContent())
                .page(createPageInfo(
                        page.getTotalElements(),
                        page.getTotalPages(),
                        page.getNumber(),
                        page.getSize())
                )
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

    public Pageable createPageable(Integer number, Integer size, Sort sort) {
        if (size == null) {
            size = paginationProperties.getDefaultPageSize();
        } else if (size > paginationProperties.getMaxPageSize()) {
            size = paginationProperties.getMaxPageSize();
        }
        return PageRequest.of(
                number, size,
                sort
        );
    }
}


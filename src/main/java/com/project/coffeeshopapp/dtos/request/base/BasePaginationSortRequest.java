package com.project.coffeeshopapp.dtos.request.base;

import com.project.coffeeshopapp.enums.SortDirection;
import com.project.coffeeshopapp.enums.SortField;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BasePaginationSortRequest<T extends SortField> {
    @Min(value = 0, message = "Page index must not be less than zero")
    private Integer page = 0;

    @Positive(message = "Size must be greater than zero")
    private Integer size;

    private List<T> sortBy = Collections.emptyList();

    private List<SortDirection> sortDir = Collections.singletonList(SortDirection.ASC);

    // Add a method to get sortBy, ensuring it's initialized if null or empty
    public List<T> getSortBy() {
        if (sortBy == null || sortBy.isEmpty()) {
            return initSortBy();  // Call the method to set the default sortBy
        }
        return sortBy;
    }

    /**
     * Abstract method to initialize sortBy.
     * Each subclass must provide its own implementation.
     */
    protected abstract List<T> initSortBy();
}


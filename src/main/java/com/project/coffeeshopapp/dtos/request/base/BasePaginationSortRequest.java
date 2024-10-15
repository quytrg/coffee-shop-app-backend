package com.project.coffeeshopapp.dtos.request.base;

import com.project.coffeeshopapp.enums.BaseEnum;
import com.project.coffeeshopapp.enums.SortDirection;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Collections;
import java.util.List;


@Getter
@Setter
@ToString(callSuper = true)
public abstract class BasePaginationSortRequest<T extends BaseEnum> {
    @Min(value = 0, message = "Page index must not be less than zero")
    private Integer page = 0;

    @Positive(message = "Size must be greater than zero")
    private Integer size;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<T> sortBy = Collections.emptyList();

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<SortDirection> sortDir = Collections.emptyList();

    // Add a method to get sortBy, ensuring it's initialized if null or empty
    public List<T> getSortBy() {
        if (sortBy == null || sortBy.isEmpty()) {
            setSortBy(initSortBy()); // Call the method to set the default sortBy
        }
        return sortBy;
    }

    public void setSortBy(List<T> sortBy) {
        this.sortBy = (sortBy != null && !sortBy.isEmpty()) ? sortBy : initSortBy();
    }

    /**
     * Abstract method to initialize sortBy.
     * Each subclass must provide its own implementation.
     */
    protected abstract List<T> initSortBy();


    // Add a method to get sortDir, ensuring it's initialized if null or empty
    public List<SortDirection> getSortDir() {
        if (sortDir == null || sortDir.isEmpty()) {
            setSortDir(initSortDir()); // Call the method to set the default sortDir
        }
        return sortDir;
    }

    public void setSortDir(List<SortDirection> sortDir) {
        this.sortDir = (sortDir != null && !sortDir.isEmpty()) ? sortDir : initSortDir();
    }

    /**
     * Abstract method to initialize sortDir.
     * Each subclass must provide its own implementation.
     */
    protected abstract List<SortDirection> initSortDir();
}


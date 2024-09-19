package com.project.coffeeshopapp.dtos.request.category;

import com.project.coffeeshopapp.enums.CategorySortField;
import com.project.coffeeshopapp.enums.SortDirection;
import com.project.coffeeshopapp.properties.PaginationProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorySearchRequest {
    @Min(0)
    private Integer page = 0;

    @Positive
    private Integer size;

    private List<CategorySortField> sortBy = Collections.singletonList(CategorySortField.NAME);

    private List<SortDirection> sortDir = Collections.singletonList(SortDirection.ASC);
}

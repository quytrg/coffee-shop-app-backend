package com.project.coffeeshopapp.utils;

import com.project.coffeeshopapp.enums.CategorySortField;
import com.project.coffeeshopapp.enums.SortDirection;
import com.project.coffeeshopapp.enums.SortField;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SortUtil {
    public Sort createSort(List<? extends SortField> sortBy, List<SortDirection> sortDir) {
        List<Sort.Order> orders = new ArrayList<>();

        for (int i = 0; i < sortBy.size(); i++) {
            String property = sortBy.get(i).getValue();
            SortDirection direction = (sortDir.size() > i) ? sortDir.get(i) : sortDir.get(0);

            Sort.Order order = (direction == SortDirection.DESC) ?
                    Sort.Order.desc(property) :
                    Sort.Order.asc(property);
            orders.add(order);
        }

        return Sort.by(orders);
    }
}

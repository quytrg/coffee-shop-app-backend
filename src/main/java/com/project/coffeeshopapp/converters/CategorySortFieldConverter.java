package com.project.coffeeshopapp.converters;

import com.project.coffeeshopapp.enums.CategorySortField;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategorySortFieldConverter implements Converter<String, CategorySortField> {
    @Override
    public CategorySortField convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        for (CategorySortField field : CategorySortField.values()) {
            if (field.getValue().equalsIgnoreCase(source)) {
                return field;
            }
        }
        throw new IllegalArgumentException("Invalid sortBy value: " + source);
    }
}

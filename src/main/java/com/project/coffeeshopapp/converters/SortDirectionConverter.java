//package com.project.coffeeshopapp.converters;
//
//import com.project.coffeeshopapp.enums.SortDirection;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SortDirectionConverter implements Converter<String, SortDirection> {
//    @Override
//    public SortDirection convert(String source) {
//        if (source == null || source.isEmpty()) {
//            return null;
//        }
//        for (SortDirection direction : SortDirection.values()) {
//            if (direction.getValue().equalsIgnoreCase(source)) {
//                return direction;
//            }
//        }
//        throw new IllegalArgumentException("Invalid sortDir value: " + source);
//    }
//}

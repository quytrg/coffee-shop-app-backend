package com.project.coffeeshopapp.factories;

import com.project.coffeeshopapp.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

/**
 * Implement a ConverterFactory that produces converters for any enum type extending BaseEnum.
 * This factory leverages generics to handle multiple enum types dynamically.
 */
@Component
public class StringToEnumConverterFactory implements ConverterFactory<String, BaseEnum> {

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnumConverter<>(targetType);
    }

    /**
     * Inner class that performs the actual conversion from String to Enum.
     *
     * @param <T> The type of Enum extending BaseEnum.
     */
    private static class StringToEnumConverter<T extends BaseEnum> implements Converter<String, T> {
        private final Class<T> enumType;

        public StringToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            if (source == null || source.trim().isEmpty()) {
                return null;
            }
            for (T enumConstant : enumType.getEnumConstants()) {
                if (enumConstant.getValue().equalsIgnoreCase(source.trim())) {
                    return enumConstant;
                }
            }
            throw new IllegalArgumentException("Invalid enum value: " + source);
        }
    }
}


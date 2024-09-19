package com.project.coffeeshopapp.properties;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "pagination")
@Getter
@Setter
@Validated
public class PaginationProperties {
    @Min(1)
    private int defaultPageSize;
    @Max(100)
    private int maxPageSize;
}


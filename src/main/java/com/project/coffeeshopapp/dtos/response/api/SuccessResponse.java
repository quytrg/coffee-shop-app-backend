package com.project.coffeeshopapp.dtos.response.api;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse<T> {
    private int status;
    private String message;
    private T data;
    @Builder.Default
    private String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
}

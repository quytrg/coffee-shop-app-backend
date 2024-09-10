package com.project.coffeeshopapp.utils;

import com.project.coffeeshopapp.dtos.response.api.ErrorResponse;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResponseUtil {
    public <T> ResponseEntity<SuccessResponse<T>> createSuccessResponse(T data, String message, HttpStatus status) {
        return ResponseEntity.status(status).body(
                SuccessResponse.<T>builder()
                        .status(status.value())
                        .message(message)
                        .data(data)
                        .build()
        );
    }

    public ResponseEntity<ErrorResponse> createErrorResponse(String message, HttpStatus status, List<ErrorResponse.ErrorDetail> errors) {
        return ResponseEntity.status(status).body(
                ErrorResponse.builder()
                        .status(status.value())
                        .message(message)
                        .errors(errors)
                        .build()
        );
    }

    public ResponseEntity<SuccessResponse<?>> createSuccessResponseWithoutData(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(
                SuccessResponse.builder()
                        .status(status.value())
                        .message(message)
                        .build()
        );
    }
}

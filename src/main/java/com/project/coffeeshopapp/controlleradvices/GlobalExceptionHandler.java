package com.project.coffeeshopapp.controlleradvices;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.project.coffeeshopapp.customexceptions.*;
import com.project.coffeeshopapp.dtos.response.api.ErrorResponse;
import com.project.coffeeshopapp.utils.ResponseUtil;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ResponseUtil responseUtil;

    @ExceptionHandler(ImageAlreadyAssociatedException.class)
    public ResponseEntity<ErrorResponse> handleImageAlreadyAssociatedException(ImageAlreadyAssociatedException ex) {
        List<ErrorResponse.ErrorDetail> errorDetails = Collections.singletonList(
                ErrorResponse.ErrorDetail.builder()
                        .field("N/A")
                        .message(ex.getMessage())
                        .build()
        );

        return responseUtil.createErrorResponse(
                "Data constraints violation",
                HttpStatus.BAD_REQUEST,
                errorDetails
        );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleDisabledException(DisabledException ex) {
        List<ErrorResponse.ErrorDetail> errorDetails = Collections.singletonList(
                ErrorResponse.ErrorDetail.builder()
                        .field("user")
                        .message("User role is inactive")
                        .build()
        );

        return responseUtil.createErrorResponse(
                "Authentication failed",
                HttpStatus.UNAUTHORIZED,
                errorDetails
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String parameterName = ex.getName();
        String invalidValue = ex.getValue().toString();
        String expectedType = ex.getRequiredType().getSimpleName();

        String errorMessage = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s.",
                invalidValue, parameterName, expectedType);

        List<ErrorResponse.ErrorDetail> errorDetails = Collections.singletonList(
                ErrorResponse.ErrorDetail.builder()
                        .field(parameterName)
                        .message(errorMessage)
                        .build()
        );

        return responseUtil.createErrorResponse(
                "Invalid parameter value.",
                HttpStatus.BAD_REQUEST,
                errorDetails
        );
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException ex) {
        List<ErrorResponse.ErrorDetail> errorDetails = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> ErrorResponse.ErrorDetail.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        return responseUtil.createErrorResponse(
                "Invalid request parameters.",
                HttpStatus.BAD_REQUEST,
                errorDetails
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        List<ErrorResponse.ErrorDetail> errorDetails = Collections.singletonList(
                ErrorResponse.ErrorDetail.builder()
                        .field("system")
                        .message(ex.getMessage())
                        .build()
        );

        return responseUtil.createErrorResponse(
                "An unexpected error occurred.",
                HttpStatus.INTERNAL_SERVER_ERROR,
                errorDetails
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ErrorResponse.ErrorDetail> errorDetails = new ArrayList<>();

        bindingResult.getFieldErrors().forEach(fieldError -> {
            String message = fieldError.getDefaultMessage();

            // Check if error relate to type data error
            if (fieldError.getCode().equals(TypeMismatchException.ERROR_CODE)) {
                message = String.format("Invalid value '%s' for parameter '%s'.",
                        fieldError.getRejectedValue(),
                        fieldError.getField());
            }

            errorDetails.add(ErrorResponse.ErrorDetail.builder()
                    .field(fieldError.getField())
                    .message(message)
                    .build());
        });

        return responseUtil.createErrorResponse(
                "Validation failed",
                HttpStatus.BAD_REQUEST,
                errorDetails
        );
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        List<ErrorResponse.ErrorDetail> errorDetails = ex.getConstraintViolations()
                .stream()
                .map(violation -> ErrorResponse.ErrorDetail.builder()
                        .field(violation.getPropertyPath().toString())
                        .message(violation.getMessage())
                        .build())
                .toList();

        return responseUtil.createErrorResponse(
                "Validation failed",
                HttpStatus.BAD_REQUEST,
                errorDetails
        );
    }

    // handle exceptions that be thrown due to violate data constraints of database
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        List<ErrorResponse.ErrorDetail> errorDetails = Collections.singletonList(
                ErrorResponse.ErrorDetail.builder()
                        .field("Unknown") // Hoặc bạn có thể tinh chỉnh trường này nếu có thể
                        .message(ex.getMostSpecificCause().getMessage())
                        .build()
        );

        return responseUtil.createErrorResponse(
                "Data constraints violation",
                HttpStatus.BAD_REQUEST,
                errorDetails
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        List<ErrorResponse.ErrorDetail> errorDetails = Collections.singletonList(
                ErrorResponse.ErrorDetail.builder()
                        .field("phoneNumber or password")
                        .message("Invalid phone number or password")
                        .build()
        );

        return responseUtil.createErrorResponse(
                "Authentication failed",
                HttpStatus.UNAUTHORIZED,
                errorDetails
        );
    }

    @ExceptionHandler(InvalidParamException.class)
    public ResponseEntity<ErrorResponse> handleInvalidParamException(InvalidParamException ex) {
        List<ErrorResponse.ErrorDetail> errorDetails = new ArrayList<>();
        return responseUtil.createErrorResponse(
                "Invalid parameters provided",
                HttpStatus.BAD_REQUEST,
                errorDetails
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        List<ErrorResponse.ErrorDetail> errorDetails = Collections.singletonList(
                ErrorResponse.ErrorDetail.builder()
                        .field("phoneNumber")
                        .message(ex.getMessage())
                        .build()
        );

        return responseUtil.createErrorResponse(
                "User not found",
                HttpStatus.NOT_FOUND,
                errorDetails
        );
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(DataNotFoundException ex) {
        List<ErrorResponse.ErrorDetail> errorDetails = Collections.singletonList(
                ErrorResponse.ErrorDetail.builder()
                        .field(ex.getEntityName())
                        .message(ex.getMessage())
                        .build()
        );

        return responseUtil.createErrorResponse(
                "Data not found",
                HttpStatus.NOT_FOUND,
                errorDetails
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        List<ErrorResponse.ErrorDetail> errorDetails = Collections.singletonList(
                ErrorResponse.ErrorDetail.builder()
                        .field("N/A")
                        .message(ex.getMessage())
                        .build()
        );

        return responseUtil.createErrorResponse(
                "Invalid parameter value.",
                HttpStatus.BAD_REQUEST,
                errorDetails
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex) {
        List<ErrorResponse.ErrorDetail> errorDetails = Collections.singletonList(
                ErrorResponse.ErrorDetail.builder()
                        .field("N/A")
                        .message(ex.getMessage())
                        .build()
        );

        return responseUtil.createErrorResponse(
                "Data constraints violation",
                HttpStatus.BAD_REQUEST,
                errorDetails
        );
    }

    // Generic handler for deserialization errors while processing the request
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException invalidFormatException) {
            Class<?> targetType = invalidFormatException.getTargetType();
            String invalidValue = invalidFormatException.getValue().toString();
            String fieldName = invalidFormatException.getPath().get(0).getFieldName();

            // Check if the field causing the issue is related to parse LocalDateTime
            if (LocalDateTime.class.isAssignableFrom(targetType)) {
                List<ErrorResponse.ErrorDetail> errorDetails = Collections.singletonList(
                        ErrorResponse.ErrorDetail.builder()
                                .field(fieldName)
                                .message("Invalid date format for field '" + fieldName + "'. Expected format: 'yyyy-MM-dd'T'HH:mm:ss'.")
                                .build()
                );

                return responseUtil.createErrorResponse(
                        "Invalid request body.",
                        HttpStatus.BAD_REQUEST,
                        errorDetails
                );
            }

            // Check if the field causing the issue is related to enum deserialization
            if (targetType.isEnum()) {
                // Generic error detail for enum violation without exposing allowed values
                List<ErrorResponse.ErrorDetail> errorDetails = Collections.singletonList(
                        ErrorResponse.ErrorDetail.builder()
                                .field(fieldName)
                                .message("Invalid value '" + invalidValue + "' for field '" + fieldName + "'.")
                                .build()
                );

                return responseUtil.createErrorResponse(
                        "Invalid request body.",
                        HttpStatus.BAD_REQUEST,
                        errorDetails
                );
            }
        }

        List<ErrorResponse.ErrorDetail> errorDetails = Collections.singletonList(
                ErrorResponse.ErrorDetail.builder()
                        .field("system")
                        .message("An error occurred while processing the request.")
                        .build()
        );
        // Handle other HttpMessageNotReadableException causes
        return responseUtil.createErrorResponse(
                "Invalid request body.",
                HttpStatus.BAD_REQUEST,
                errorDetails
        );
    }

    @ExceptionHandler(UnitMismatchException.class)
    public ResponseEntity<ErrorResponse> handleUnitMismatchException(UnitMismatchException ex) {
        List<ErrorResponse.ErrorDetail> errorDetails = Collections.singletonList(
                ErrorResponse.ErrorDetail.builder()
                        .field("unit")
                        .message(ex.getMessage())
                        .build()
        );

        return responseUtil.createErrorResponse(
                "Invalid request body.",
                HttpStatus.BAD_REQUEST,
                errorDetails
        );
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOperationException(InvalidOperationException ex) {
        List<ErrorResponse.ErrorDetail> errorDetails = Collections.singletonList(
                ErrorResponse.ErrorDetail.builder()
                        .field("N/A")
                        .message(ex.getMessage())
                        .build()
        );

        return responseUtil.createErrorResponse(
                "Invalid operation",
                HttpStatus.BAD_REQUEST,
                errorDetails
        );
    }
}

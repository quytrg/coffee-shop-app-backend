package com.project.coffeeshopapp.controlleradvices;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.project.coffeeshopapp.customexceptions.DataNotFoundException;
import com.project.coffeeshopapp.customexceptions.InvalidEnumValueException;
import com.project.coffeeshopapp.customexceptions.InvalidParamException;
import com.project.coffeeshopapp.dtos.response.api.ErrorResponse;
import com.project.coffeeshopapp.utils.ResponseUtil;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ResponseUtil responseUtil;

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
        List<ErrorResponse.ErrorDetail> errorDetails = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> ErrorResponse.ErrorDetail.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build())
                .toList();

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

    // handle page < 0 or size <= 0
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        List<ErrorResponse.ErrorDetail> errorDetails = Collections.singletonList(
                ErrorResponse.ErrorDetail.builder()
                        .field("page or size")
                        .message(ex.getMessage())
                        .build()
        );

        return responseUtil.createErrorResponse(
                "Data constraints violation",
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

            // Check if the field causing the issue is related to enum deserialization
            if (invalidFormatException.getTargetType().isEnum()) {
                String invalidValue = invalidFormatException.getValue().toString();
                String fieldName = invalidFormatException.getPath().get(0).getFieldName();

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

//    @ExceptionHandler(InvalidEnumValueException.class)
//    public ResponseEntity<ErrorResponse> handleInvalidEnumValueException(InvalidEnumValueException ex) {
//        List<ErrorResponse.ErrorDetail> errorDetails = Collections.singletonList(
//                ErrorResponse.ErrorDetail.builder()
//                        .field(ex.getFieldName())
//                        .message(ex.getMessage())
//                        .build()
//        );
//        // Handle other HttpMessageNotReadableException causes
//        return responseUtil.createErrorResponse(
//                "Invalid request body.",
//                HttpStatus.BAD_REQUEST,
//                errorDetails
//        );
//    }
}

package com.project.coffeeshopapp.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.coffeeshopapp.customexceptions.UnauthorizedException;
import com.project.coffeeshopapp.dtos.response.api.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // proceed with the rest of the filter chain
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException e) {
            handleException(response, HttpStatus.UNAUTHORIZED, "Unauthorized", e.getMessage());
        } catch (Exception e) {
            handleException(response, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "An unexpected error occurred");
        }
    }

    private void handleException(HttpServletResponse response, HttpStatus status, String error, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .message(message)
                .errors(Collections.singletonList(ErrorResponse.ErrorDetail.builder()
                                .field("N/A")
                                .message(error)
                                .build()))
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}

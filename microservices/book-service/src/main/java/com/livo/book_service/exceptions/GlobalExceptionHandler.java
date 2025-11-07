package com.livo.book_service.exceptions;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(Exception ex,
                                                  HttpServletRequest request) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex, request);
    }

    private ResponseEntity<ApiError> buildError(HttpStatus status,
                                                String errorTitle,
                                                Exception ex,
                                                HttpServletRequest request) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                status.value(),
                errorTitle,
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }
}

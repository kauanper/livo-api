package com.livo.book_service.exceptions;


import com.livo.book_service.exceptions.custom.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiError> handleBookNotFound(BookNotFoundException ex,
                                                       HttpServletRequest request) {
        return buildError(HttpStatus.NOT_FOUND, "Book Not Found", ex, request);
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<ApiError> handleExternalApi(ExternalApiException ex,
                                                      HttpServletRequest request) {
        return buildError(HttpStatus.BAD_GATEWAY, "External API Error", ex, request);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiError> handleInvalidRequest(InvalidRequestException ex,
                                                         HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, "Invalid Request", ex, request);
    }

    @ExceptionHandler(EmptyBookListException.class)
    public ResponseEntity<ApiError> handleEmptyBookList(EmptyBookListException ex,
                                                        HttpServletRequest request) {
        return buildError(HttpStatus.NOT_FOUND, "Empty Book List", ex, request);
    }

    @ExceptionHandler(OrderByInvalidException.class)
    public ResponseEntity<ApiError> handleOrderby(OrderByInvalidException ex,
                                                  HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, "OrderBy Invalid", ex, request);
    }

    @ExceptionHandler(TypeInvalidException.class)
    public ResponseEntity<ApiError> handleType(TypeInvalidException ex,
                                                  HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, "Type Invalid", ex, request);
    }

    @ExceptionHandler(InvalidBookStatusException.class)
    public ResponseEntity<ApiError> handleInvalidBookStatus(InvalidBookStatusException ex,
                                                             HttpServletRequest request) {
        return buildError(HttpStatus.FORBIDDEN, "Invalid Book Status", ex, request);
    }

    @ExceptionHandler(RatingAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleRatingAlreadyExists(RatingAlreadyExistsException ex,
                                                               HttpServletRequest request) {
        return buildError(HttpStatus.CONFLICT, "Rating Already Exists", ex, request);
    }

    @ExceptionHandler(RatingNotFoundException.class)
    public ResponseEntity<ApiError> handleRatingNotFound(RatingNotFoundException ex,
                                                          HttpServletRequest request) {
        return buildError(HttpStatus.NOT_FOUND, "Rating Not Found", ex, request);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingParam(MissingServletRequestParameterException ex,
                                                       HttpServletRequest request) {
        String message = String.format("O parâmetro obrigatório '%s' está faltando.", ex.getParameterName());
        return buildError(HttpStatus.BAD_REQUEST, "Missing Request Parameter",
                new InvalidRequestException(message), request);
    }

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

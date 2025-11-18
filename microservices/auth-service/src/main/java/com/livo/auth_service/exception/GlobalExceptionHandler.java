package com.livo.auth_service.exception;

import com.livo.auth_service.user_client.UserClientException;
import feign.FeignException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponseDTO>> handleValidationException(MethodArgumentNotValidException e) {
        List<ErrorResponseDTO> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorResponseDTO(
                        error.getField(),
                        error.getDefaultMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase()
                ))
                .toList();

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(JwtException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponseDTO error = new ErrorResponseDTO(
                e.getCause().toString(),
                e.getMessage(),
                status.value(),
                status.getReasonPhrase()
        );
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(ResponseStatusException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponseDTO error = new ErrorResponseDTO(
                e.getCause().toString(),
                e.getMessage(),
                status.value(),
                status.getReasonPhrase()
        );
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UserClientException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserClientException(UserClientException e, WebRequest request) {
        String message = Optional.ofNullable(e.getMessage()).orElse("Erro no serviço de usuários");
        log.warn("UserClientException: {}", message);

        HttpStatus status = e.getHttpStatus();
        ErrorResponseDTO body = new ErrorResponseDTO(
                e.getCause().toString(),
                e.getMessage(),
                status.value(),
                status.getReasonPhrase()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    public ResponseEntity<ErrorResponseDTO> handleFeignException(FeignException ex, WebRequest request) {
        String message = Optional.ofNullable(ex.getMessage()).orElse("Erro de comunicação com serviço externo");
        log.error("FeignException: status={}, message={}", ex.status(), message);

        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        ErrorResponseDTO body = new ErrorResponseDTO(
                ex.getCause().toString(),
                ex.getMessage(),
                status.value(),
                status.getReasonPhrase()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(body);
    }

    public ResponseEntity<ErrorResponseDTO> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String msg = Optional.ofNullable(ex.getReason()).orElse(ex.getMessage());
        log.warn("ResponseStatusException: {} -> {}", status.value(), msg);

        ErrorResponseDTO body = new ErrorResponseDTO(
                ex.getCause().toString(),
                ex.getMessage(),
                status.value(),
                status.getReasonPhrase()
        );
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleAllExceptions(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponseDTO error = new ErrorResponseDTO(
                ex.getCause().toString(),
                ex.getMessage(),
                status.value(),
                status.getReasonPhrase()
        );
        return ResponseEntity.status(status).body(error);
    }
}

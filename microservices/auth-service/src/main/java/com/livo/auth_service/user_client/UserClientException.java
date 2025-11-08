package com.livo.auth_service.user_client;

import org.springframework.http.HttpStatus;

/**
 * Exceção customizada para erros na comunicação com User-Service.
 */
public class UserClientException extends RuntimeException {
    private final HttpStatus httpStatus;

    public UserClientException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public UserClientException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}


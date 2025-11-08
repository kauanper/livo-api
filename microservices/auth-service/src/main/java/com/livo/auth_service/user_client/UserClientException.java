package com.livo.auth_service.user_client;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
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

}


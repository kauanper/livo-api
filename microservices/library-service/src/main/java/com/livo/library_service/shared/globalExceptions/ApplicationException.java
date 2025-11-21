package com.livo.library_service.shared.globalExceptions;

import org.springframework.http.HttpStatus;

public abstract class ApplicationException extends RuntimeException {

    private final String field;
    private final HttpStatus status;

    protected ApplicationException(String field, String message, HttpStatus status) {
        super(message);
        this.field = field;
        this.status = status;
    }

    public String getField() {
        return field;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

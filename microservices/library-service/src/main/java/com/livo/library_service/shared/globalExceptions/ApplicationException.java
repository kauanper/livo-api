package com.livo.library_service.shared.globalExceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ApplicationException extends RuntimeException {

    private final String field;
    private final HttpStatus status;

    protected ApplicationException(String field, String message, HttpStatus status) {
        super(message);
        this.field = field;
        this.status = status;
    }

}

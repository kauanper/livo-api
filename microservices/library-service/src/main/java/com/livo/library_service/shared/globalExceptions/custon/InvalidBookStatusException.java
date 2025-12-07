package com.livo.library_service.shared.globalExceptions.custon;

import com.livo.library_service.shared.globalExceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class InvalidBookStatusException extends ApplicationException {
    public InvalidBookStatusException(String field, String message) {
        super(field, message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}

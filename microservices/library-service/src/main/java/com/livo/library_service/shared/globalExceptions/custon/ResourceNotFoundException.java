package com.livo.library_service.shared.globalExceptions.custon;

import com.livo.library_service.shared.globalExceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApplicationException {
    public ResourceNotFoundException(String field) {
        super(field, "recurso nao encontrado", HttpStatus.NOT_FOUND);
    }
    public ResourceNotFoundException(String field, String message) {
        super(field, message, HttpStatus.NOT_FOUND);
    }

}

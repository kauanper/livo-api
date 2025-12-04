package com.livo.library_service.library.custonExceptions;

import com.livo.library_service.shared.globalExceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class DeniedAccessException extends ApplicationException {
    public DeniedAccessException(String message) {
        super(
                message,
                "User ID",
                HttpStatus.FORBIDDEN
        );
    }
}

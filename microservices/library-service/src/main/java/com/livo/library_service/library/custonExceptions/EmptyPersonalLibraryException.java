package com.livo.library_service.library.custonExceptions;

import com.livo.library_service.shared.globalExceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class EmptyPersonalLibraryException extends ApplicationException {
    public EmptyPersonalLibraryException() {
        super(
                null,
                String.format("Sua biblioteca pessoal ainda está vazia."),
                HttpStatus.OK
        );
    }
}

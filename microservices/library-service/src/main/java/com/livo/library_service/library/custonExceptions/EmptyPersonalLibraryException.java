package com.livo.library_service.library.custonExceptions;

import com.livo.library_service.shared.globalExceptions.ApplicationException;

public class EmptyPersonalLibraryException extends ApplicationException {
    public EmptyPersonalLibraryException() {
        super(
                null,
                String.format("Sua biblioteca pessoal ainda está vazia.")
        );
    }
}

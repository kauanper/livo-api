package com.livo.library_service.library.custonExceptions;

import com.livo.library_service.shared.globalExceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class ExistingAssociationException extends ApplicationException {
    public ExistingAssociationException(String entity) {
        super(
                "Usuário e " + entity,
                "Usuário já possui esse livro na " + entity + " pessoal",
                HttpStatus.CONFLICT
        );
    }
}

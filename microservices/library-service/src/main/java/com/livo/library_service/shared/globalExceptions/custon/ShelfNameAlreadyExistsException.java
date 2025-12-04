package com.livo.library_service.shared.globalExceptions.custon;

import com.livo.library_service.shared.globalExceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class ShelfNameAlreadyExistsException extends ApplicationException {
    public ShelfNameAlreadyExistsException(String shelfName) {
        super(
                "name",
                String.format("Já existe uma pratilheira com o nome '%s' para este usuário.", shelfName),
                HttpStatus.CONFLICT
        );
    }
}



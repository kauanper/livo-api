package com.livo.library_service.library.custonExceptions;

import com.livo.library_service.shared.globalExceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class ExistingAssociationException extends ApplicationException {
    public ExistingAssociationException() {
        super(
                "UserId e BookId",
                String.format("Usuário já possui esse livro em sua biblioteca pessoal"),
                HttpStatus.CONFLICT
        );
    }
}

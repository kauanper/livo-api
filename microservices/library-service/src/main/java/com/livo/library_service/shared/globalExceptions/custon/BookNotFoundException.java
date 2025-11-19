package com.livo.library_service.shared.globalExceptions.custon;

import com.livo.library_service.shared.globalExceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class BookNotFoundException extends ApplicationException {
    public BookNotFoundException(String bookId) {
        super(
                "bookId",
                String.format("Livro com o ID '%s' não foi encontrado.", bookId),
                HttpStatus.NOT_FOUND
        );
    }
}

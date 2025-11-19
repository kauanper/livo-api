package com.livo.library_service.library.custonExceptions;

import com.livo.library_service.shared.globalExceptions.ApplicationException;

public class BookNotFoundException extends ApplicationException {
    public BookNotFoundException(String bookId) {
        super(
                "bookId",
                String.format("Livro com o ID '%s' não foi encontrado.", bookId)
        );
    }
}

package com.livo.book_service.exceptions.custom;

public class TypeInvalidException extends RuntimeException {
    public TypeInvalidException(String message) {
        super(message);
    }
}

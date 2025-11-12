package com.livo.book_service.exceptions.custom;

public class EmptyBookListException extends RuntimeException {
    public EmptyBookListException(String message) {
        super(message);
    }
}

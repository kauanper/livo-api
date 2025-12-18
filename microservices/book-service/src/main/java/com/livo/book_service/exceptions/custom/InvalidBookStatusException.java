package com.livo.book_service.exceptions.custom;

public class InvalidBookStatusException extends RuntimeException {
    public InvalidBookStatusException(String message) {
        super(message);
    }
}


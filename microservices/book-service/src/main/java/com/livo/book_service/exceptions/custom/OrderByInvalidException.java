package com.livo.book_service.exceptions.custom;

public class OrderByInvalidException extends RuntimeException {
    public OrderByInvalidException(String message) {
        super(message);
    }
}

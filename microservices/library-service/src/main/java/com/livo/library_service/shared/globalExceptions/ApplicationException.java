package com.livo.library_service.shared.globalExceptions;

public abstract class ApplicationException extends RuntimeException {
    private final String field;

    protected ApplicationException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}

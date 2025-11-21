package com.livo.auth_service.exception;

public record ErrorResponseDTO(String field, String message, Integer statusCode, String error) {
}

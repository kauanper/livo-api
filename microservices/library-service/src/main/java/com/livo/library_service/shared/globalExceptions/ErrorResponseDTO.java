package com.livo.library_service.shared.globalExceptions;

public record ErrorResponseDTO(String field, String message, Integer statusCode, String error) {
}

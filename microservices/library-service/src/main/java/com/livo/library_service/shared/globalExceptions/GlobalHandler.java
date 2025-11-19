package com.livo.library_service.shared.globalExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String fieldName = ex.getName();
        String invalidValue = ex.getValue() != null ? ex.getValue().toString() : "null";
        String requiredType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown";
        ErrorResponseDTO error = new ErrorResponseDTO(
                fieldName,
                String.format("Valor '%s' é inválido para o campo '%s'. O tipo esperado é %s.", invalidValue, fieldName, requiredType),
                HttpStatus.BAD_REQUEST.value(),
                "PathVariable Type Mismatch"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponseDTO> handleApplicationException(ApplicationException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                ex.getField(),
                ex.getMessage(),
                ex.getStatus().value(),
                ex.getClass().getSimpleName()
        );

        return ResponseEntity.status(ex.getStatus()).body(error);
    }

}

package com.livo.user_service.utils.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;
import java.util.Map;

@RestControllerAdvice
public class JwtExceptionHandler {
    @ExceptionHandler({
            ExpiredJwtException.class,
            SignatureException.class,
            MalformedJwtException.class,
            JwtException.class,
            IllegalArgumentException.class // usado quando header está ausente
    })
    public ResponseEntity<Map<String, String>> handleJwtErrors(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "error", "tem algo de errado com acessToken aew :p",
                        "message", ex.getMessage()
                ));
    }
}

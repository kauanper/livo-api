package com.livo.exemplo_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExemploController {

    @Value("${message:Fallback caso Config Server falhe}")
    private String message;

    @GetMapping("/message")
    public String getMessage() {
        return message;
    }
}




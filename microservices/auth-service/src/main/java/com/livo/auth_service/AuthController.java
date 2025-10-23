package com.livo.auth_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @GetMapping("/test")
    public String testEndpoint() {
        System.out.println("Endpoint de teste chamado!");
        return "Auth-Service funcionando!";
    }
}

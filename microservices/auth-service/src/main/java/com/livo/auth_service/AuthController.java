package com.livo.auth_service;

import com.livo.auth_service.services.GenerateTokenUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final GenerateTokenUseCase generateTokenUseCase;

    public AuthController(GenerateTokenUseCase generateTokenUseCase) {
        this.generateTokenUseCase = generateTokenUseCase;
    }

    @GetMapping("/test")
    public String testEndpoint() {
        System.out.println("Endpoint de teste chamado!");
        return "Auth-Service funcionando!";
    }

    @GetMapping("/token")
    public String generateToken(@RequestParam String username) {
        String token = generateTokenUseCase.execute(username);
        System.out.println("Token gerado para " + username + ": " + token);
        return token;
    }
}

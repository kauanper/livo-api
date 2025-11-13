package com.livo.oauth2_service.controller;

import com.livo.oauth2_service.TokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/login")
    public String login(@RequestParam String email) {
        // Aqui normalmente você validaria o usuário no banco
        return TokenService.gerarTokenDeAcesso(email);
    }
}

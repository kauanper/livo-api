package com.livo.auth_service.auth.test;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth/auth-protected")
public class AuthTestController {

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Usuário acessou a rota protegida com sucesso\n");
    }
}

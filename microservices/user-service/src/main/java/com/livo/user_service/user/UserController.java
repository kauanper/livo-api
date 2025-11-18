package com.livo.user_service.user;

import com.livo.user_service.user.dto.UserAuthRequest;
import com.livo.user_service.user.dto.UserAuthResponse;
import com.livo.user_service.user.dto.UserDto;
import com.livo.user_service.user.dto.UserRegisterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserRegisterDTO dto) {
        return userService.register(dto);
    }
    
    @GetMapping
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("User service funcionando!");
    }

    // Endpoint interno para autenticação de usuário.
    // Usado pelo Auth-Service para validar credenciais.
    @PostMapping("/internal/authenticate")
    public ResponseEntity<UserAuthResponse> authenticate(@RequestBody @Valid UserAuthRequest request) {
        return userService.authenticate(request);
    }

    // Endpoint interno para buscar usuário por ID.
    // Usado pelo Auth-Service para obter dados do usuário.
    @GetMapping("/internal/users/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") UUID id) {
        return userService.getById(id);
    }

    // Endpoint interno para buscar usuário por email.
    // Usado pelo Auth-Service para obter dados do usuário.
    @GetMapping("/internal/users/email/{email}")
    public ResponseEntity<UserDto> getByEmail(@PathVariable("email") String email) {
        return userService.getByEmail(email);
    }
}

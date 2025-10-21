package com.livo.user_service.user;

import com.livo.user_service.user.dto.UserRegisterDTO;
import com.netflix.discovery.converters.Auto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
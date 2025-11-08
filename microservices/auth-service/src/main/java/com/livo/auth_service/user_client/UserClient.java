package com.livo.auth_service.user_client;

import com.livo.auth_service.user_client.dto.UserAuthRequest;
import com.livo.auth_service.user_client.dto.UserAuthResponse;
import com.livo.auth_service.user_client.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

// Para comunicação com User-Service.
// - Autentica usuários (validar email + senha)
// - Busca dados do usuário por ID
// - Busca dados do usuário por email
// 
// As senhas são enviadas apenas durante a requisição HTTP (via TLS)
// nunca são armazenadas ou logadas no Auth-Service.
@FeignClient(
    name = "user-service",
    configuration = UserClientConfig.class
    // fallback = UserClientFallback.class // Lembrar de descomentar quando configurar circuit breaker
)
public interface UserClient {

    // Autentica um usuário validando email e senha.
    // @param request Requisição contendo email e senha
    // @return Resposta com dados do usuário autenticado (sem senha)
    // @throws UserClientException se credenciais inválidas ou serviço indisponível
    @PostMapping("/internal/authenticate")
    ResponseEntity<UserAuthResponse> authenticate(@RequestBody UserAuthRequest request);

    // Busca dados do usuário por ID.
    // @param id UUID do usuário
    // @return Dados do usuário (sem senha)
    // @throws UserClientException se usuário não encontrado ou serviço indisponível
    @GetMapping("/internal/users/{id}")
    ResponseEntity<UserDto> getById(@PathVariable("id") UUID id);

    // Busca dados do usuário por email.
    // @param email Email do usuário
    // @return Dados do usuário (sem senha)
    // @throws UserClientException se usuário não encontrado ou serviço indisponível
    @GetMapping("/internal/users/email/{email}")
    ResponseEntity<UserDto> getByEmail(@PathVariable("email") String email);
}


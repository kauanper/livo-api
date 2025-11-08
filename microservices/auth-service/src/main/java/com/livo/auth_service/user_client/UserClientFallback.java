package com.livo.auth_service.user_client;

import com.livo.auth_service.user_client.dto.UserAuthResponse;
import com.livo.auth_service.user_client.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Fallback para UserClient quando o User-Service está indisponível.
 * Implementa circuit breaker pattern para evitar falhas em cascata.
 */
@Slf4j
@Component
public class UserClientFallback implements UserClient {

    @Override
    public ResponseEntity<UserAuthResponse> authenticate(UserAuthRequest request) {
        log.error("Fallback acionado: User-Service indisponível para autenticação do usuário: {}", request.getEmail());
        throw new UserClientException(
            "Serviço de usuários temporariamente indisponível. Tente novamente mais tarde.",
            HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    @Override
    public ResponseEntity<UserDto> getById(UUID id) {
        log.error("Fallback acionado: User-Service indisponível para buscar usuário por ID: {}", id);
        throw new UserClientException(
            "Serviço de usuários temporariamente indisponível. Tente novamente mais tarde.",
            HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    @Override
    public ResponseEntity<UserDto> getByEmail(String email) {
        log.error("Fallback acionado: User-Service indisponível para buscar usuário por email: {}", email);
        throw new UserClientException(
            "Serviço de usuários temporariamente indisponível. Tente novamente mais tarde.",
            HttpStatus.SERVICE_UNAVAILABLE
        );
    }
}


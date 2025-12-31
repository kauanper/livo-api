package com.livo.library_service.shared.clients;

import com.livo.library_service.shared.dtos.user.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service", url = "${USER_SERVICE_URL:localhost:8082}", configuration = UserClientConfig.class
// fallback = UserClientFallback.class // Lembrar de descomentar quando
// configurar circuit breaker
)
public interface UserClient {
    @GetMapping("/user/internal/users/{id}")
    UserDto getById(@PathVariable("id") UUID id);
}
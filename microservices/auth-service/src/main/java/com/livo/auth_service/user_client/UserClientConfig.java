package com.livo.auth_service.user_client;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import feign.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * Configuração do Feign Client para comunicação com User-Service.
 * Inclui interceptores para autenticação entre serviços,
 * tratamento de erros e logging.
 */
@Slf4j
@Configuration
public class UserClientConfig {

    @Value("${user.service.token:}")
    private String serviceToken;

    /**
     * Interceptor para adicionar token de autenticação entre serviços.
     * Se configurado, adiciona header Authorization nas requisições.
     */
    @Bean
    public RequestInterceptor serviceAuthInterceptor() {
        return template -> {
            if (serviceToken != null && !serviceToken.isEmpty()) {
                template.header("Authorization", "Bearer " + serviceToken);
                log.debug("Adicionado token de autenticação entre serviços");
            }
        };
    }

    /**
     * Configuração de log do Feign para debugging.
     * Nível FULL para desenvolvimento (requisições e respostas completas).
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC; // FULL para debug completo, BASIC para produção
    }

    /**
     * Decodificador de erros customizado para tratamento adequado
     * de exceções HTTP do User-Service.
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();
        
        return (methodKey, response) -> {
            HttpStatus status = HttpStatus.valueOf(response.status());
            
            switch (status) {
                case UNAUTHORIZED:
                    log.warn("Credenciais inválidas ao autenticar usuário: {}", methodKey);
                    return new UserClientException("Credenciais inválidas", status);
                case NOT_FOUND:
                    log.warn("Usuário não encontrado: {}", methodKey);
                    return new UserClientException("Usuário não encontrado", status);
                case SERVICE_UNAVAILABLE:
                case GATEWAY_TIMEOUT:
                    log.error("User-Service indisponível ou timeout: {}", methodKey);
                    return new UserClientException("User-Service temporariamente indisponível", status);
                default:
                    // Usa decoder padrão para outros erros
                    log.debug("Erro HTTP {} ao comunicar com User-Service: {}", status.value(), methodKey);
                    return defaultDecoder.decode(methodKey, response);
            }
        };
    }
}

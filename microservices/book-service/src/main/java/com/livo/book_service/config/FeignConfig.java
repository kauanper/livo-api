package com.livo.book_service.config;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL; // Para debug detalhado
    }

    @Bean
    public Request.Options requestOptions() {
        // Timeout de conexão: 5 segundos
        // Timeout de leitura: 10 segundos
        return new Request.Options(5000, 10000);
    }

    @Bean
    public Retryer retryer() {
        // Retry: 3 tentativas com intervalo inicial de 100ms, máximo de 1000ms
        return new Retryer.Default(100, 1000, 3);
    }
}


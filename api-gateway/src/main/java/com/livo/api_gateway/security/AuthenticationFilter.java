package com.livo.api_gateway.security;

import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@RefreshScope
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    private RouterValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        String authHeader = request.getHeaders().getFirst("Authorization");

        log.debug("Gateway filter - path={}, Authorization present={}", path, authHeader != null);

        // Apenas valida se a rota for segura
        if (validator.isSecured.test(request)) {

            if (!request.getHeaders().containsKey("Authorization")) {
                log.warn("Missing Authorization header for secured request: {}", path);
                return this.onError(exchange, "Authorization header is missing in request");
            }

            String token = authHeader.substring(7);

            try {
                jwtUtil.validateToken(token);
                log.debug("Token valid for path={}", path);

            } catch (JwtException e) {
                log.warn("Invalid/expired token for path={}: {}", path, e.getMessage());
                return this.onError(exchange, "Authorization header is invalid or expired");
            }
        }

        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1; // garante que roda antes dos filtros padrões
    }
}

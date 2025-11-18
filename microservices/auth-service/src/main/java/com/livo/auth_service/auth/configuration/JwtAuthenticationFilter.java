package com.livo.auth_service.auth.configuration;

import com.livo.auth_service.auth.token.GenerateToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final GenerateToken generateToken;

    public JwtAuthenticationFilter(GenerateToken generateToken) {
        this.generateToken = generateToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        try {
            Claims claims = generateToken.validate(token); // lança JwtException se inválido
            String userId = claims.getSubject(); // sub
            @SuppressWarnings("unchecked")
            List<String> roles = claims.get("roles", List.class);
            Collection<SimpleGrantedAuthority> authorities = roles == null
                    ? List.of()
                    : roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

            var auth = new UsernamePasswordAuthenticationToken(userId, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response);
        } catch (JwtException ex) {
            // token inválido/expirado -> 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            String body = "{\"error\":\"invalid_token\",\"message\":\"" + ex.getMessage() + "\"}";
            response.getWriter().write(body);
            return;
        }
    }

}

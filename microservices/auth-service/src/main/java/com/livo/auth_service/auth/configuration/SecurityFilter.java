//package com.livo.auth_service.auth.configuration;
//
//import com.livo.auth_service.user_client.UserClient;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.token.TokenService;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.UUID;
//
//@Component
//public class SecurityFilter extends OncePerRequestFilter {
//    @Autowired
//    TokenService tokenService;
//
//    @Autowired
//    UserClient userClient;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        var token = this.recoverToken(request);
//        if (token != null) {
//            String AdministratorId = String.valueOf(tokenService.verifyToken(token));
//            UserDetails user = (UserDetails) userClient.getById(UUID.fromString(AdministratorId));
//            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    private String recoverToken(HttpServletRequest request) {
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader == null) return null;
//        return authHeader.replace("Bearer ", "");
//    }
//}

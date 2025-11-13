package com.livo.oauth2_service.service;

import com.livo.oauth2_service.model.dtos.AuthRequest;
import com.livo.oauth2_service.model.dtos.AuthResponse;
import com.livo.oauth2_service.model.User;
import com.livo.oauth2_service.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GoogleAuthService {

    private final UserRepository userRepository;

    public GoogleAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse loginWithGoogle(AuthRequest request) throws Exception {

        // 1️⃣ Validar ID Token com Google
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList("SEU_CLIENT_ID_WEB.apps.googleusercontent.com"))
                .build();

        GoogleIdToken idToken = verifier.verify(request.getIdToken());
        if (idToken == null) {
            throw new RuntimeException("ID Token inválido");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();

        String googleId = payload.getSubject();
        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String photoUrl = (String) payload.get("picture");

        // 2️⃣ Criar ou atualizar usuário
        User user = userRepository.findByGoogleId(googleId)
                .orElseGet(() -> new User(googleId, name, email, photoUrl));
        user.setName(name);
        user.setEmail(email);
        user.setPhotoUrl(photoUrl);
        userRepository.save(user);

        // 3️⃣ Gerar token OAuth2 interno (ou JWT) para usar na sua aplicação ou para Google Books
        AuthResponse response = new AuthResponse();
        response.setAccessToken("TOKEN_FAKE_DE_EXEMPLO"); // aqui você integraria OAuth2 real
        response.setExpiresIn(3600);
        return response;
    }
}

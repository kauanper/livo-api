package com.livo.library_service.shared.globalExceptions.custon;

import com.livo.library_service.shared.globalExceptions.ApplicationException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException(UUID userId) {
        super(
                "userId",
                String.format("Usuário com o ID '%s' não foi encontrado.", userId),
                HttpStatus.NOT_FOUND
        );
    }
}

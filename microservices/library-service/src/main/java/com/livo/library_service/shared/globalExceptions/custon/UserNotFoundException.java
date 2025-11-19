package com.livo.library_service.shared.globalExceptions.custon;

import com.livo.library_service.shared.globalExceptions.ApplicationException;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException(String userId) {
        super(
                "userId",
                String.format("Usuário com o ID '%s' não foi encontrado.", userId)
        );
    }
}

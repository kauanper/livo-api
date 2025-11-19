package com.livo.library_service.library.custonExceptions;

import com.livo.library_service.shared.globalExceptions.ApplicationException;

public class AssociationNotFoundException extends ApplicationException {
  public AssociationNotFoundException(Long id) {
    super(
            "userBookId",
            String.format("Associação (Usuário::Livro) com o ID '%s' não foi encontrada.", id)
    );
  }
}

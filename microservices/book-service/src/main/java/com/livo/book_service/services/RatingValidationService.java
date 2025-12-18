package com.livo.book_service.services;

import com.livo.book_service.APIs.LibraryClient;
import com.livo.book_service.dtos.BookStatus;
import com.livo.book_service.dtos.BookStatusResponse;
import com.livo.book_service.exceptions.custom.InvalidBookStatusException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Serviço responsável por validar o status do livro antes de permitir avaliação.
 * Apenas livros com status LIDO ou ABANDONADO podem ser avaliados.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RatingValidationService {

    private final LibraryClient libraryClient;

    // Valida se o livro possui status permitido para avaliação (LIDO ou ABANDONADO).
    public void validateBookStatusForRating(UUID userId, String bookId) {
        try {
            BookStatusResponse bookStatusResponse = libraryClient.getBookStatus(userId, bookId);
            BookStatus status = bookStatusResponse.getBookStatus();

            if (status != BookStatus.LIDO && status != BookStatus.ABANDONADO) {
                throw new InvalidBookStatusException(
                        String.format(
                                "Apenas livros com status LIDO ou ABANDONADO podem ser avaliados. " +
                                        "Status atual do livro: %s",
                                status
                        )
                );
            }

            log.debug("Validação de status bem-sucedida para livro {} do usuário {}: {}", bookId, userId, status);

        } catch (FeignException.NotFound ex) {
            log.warn("Livro {} não encontrado na biblioteca do usuário {}", bookId, userId);
            throw new InvalidBookStatusException(
                    "Livro não encontrado na sua biblioteca. " +
                            "Apenas livros com status LIDO ou ABANDONADO podem ser avaliados."
            );
        } catch (FeignException ex) {
            log.error("Erro ao consultar library-service para validar status do livro {}: {}", bookId, ex.getMessage());
            throw new InvalidBookStatusException(
                    "Erro ao validar status do livro. Tente novamente mais tarde."
            );
        }
    }
}


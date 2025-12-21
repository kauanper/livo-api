package com.livo.book_service.services;

import com.livo.book_service.APIs.LibraryClient;
import com.livo.book_service.dtos.BookStatus;
import com.livo.book_service.dtos.BookStatusResponse;
import com.livo.book_service.exceptions.custom.InvalidBookStatusException;
import com.livo.book_service.exceptions.custom.InvalidRequestException;
import com.livo.book_service.exceptions.custom.RatingAlreadyExistsException;
import com.livo.book_service.exceptions.custom.RatingNotFoundException;
import com.livo.book_service.rating.entities.BookRating;
import com.livo.book_service.rating.repositories.RatingRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingValidationService {

    private final LibraryClient libraryClient;
    private final RatingRepository ratingRepository;

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

    // Valida se a nota está no intervalo permitido (1 a 5).
    public void validateRatingRange(Integer rating) {
        if (rating == null) {
            throw new InvalidRequestException("A avaliação é obrigatória.");
        }
        if (rating < 1 || rating > 5) {
            throw new InvalidRequestException(
                    String.format("A avaliação deve estar entre 1 e 5. Valor recebido: %d", rating)
            );
        }
    }

    // Valida se já existe uma avaliação para o usuário e livro.
    public void validateRatingDoesNotExist(UUID userId, String bookId) {
        if (ratingRepository.existsByUserIdAndBookId(userId, bookId)) {
            throw new RatingAlreadyExistsException(
                    String.format("Já existe uma avaliação para o livro %s do usuário.", bookId)
            );
        }
    }

    // Valida se existe uma avaliação para o usuário e livro.
    public BookRating validateRatingExists(UUID userId, String bookId) {
        return ratingRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new RatingNotFoundException(
                        String.format("Avaliação não encontrada para o livro %s do usuário.", bookId)
                ));
    }
}


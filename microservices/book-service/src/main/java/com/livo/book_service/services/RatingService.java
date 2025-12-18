package com.livo.book_service.rating.services;

import com.livo.book_service.rating.dtos.RatingResponse;
import com.livo.book_service.rating.dtos.RatingSummaryResponse;
import com.livo.book_service.rating.entities.BookRating;
import com.livo.book_service.rating.entities.BookRatingSummary;
import com.livo.book_service.rating.repositories.RatingRepository;
import com.livo.book_service.rating.repositories.RatingSummaryRepository;
import com.livo.book_service.services.RatingValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RatingSummaryRepository ratingSummaryRepository;
    private final RatingValidationService validationService;

    @Transactional
    public RatingResponse createRating(UUID userId, String bookId, Integer rating) {
        log.info("Criando avaliação para livro {} do usuário {} com nota {}", bookId, userId, rating);

        // Validações
        validationService.validateRatingRange(rating);
        validationService.validateBookStatusForRating(userId, bookId);
        validationService.validateRatingDoesNotExist(userId, bookId);

        // Cria a avaliação
        BookRating bookRating = BookRating.builder()
                .userId(userId)
                .bookId(bookId)
                .rating(rating)
                .build();

        bookRating = ratingRepository.save(bookRating);
        log.debug("Avaliação criada com ID: {}", bookRating.getId());

        // Atualiza o resumo agregado
        updateSummaryOnCreate(bookId, rating);

        return mapToResponse(bookRating);
    }

    @Transactional
    public RatingResponse updateRating(UUID userId, String bookId, Integer rating) {
        log.info("Atualizando avaliação para livro {} do usuário {} com nova nota {}", bookId, userId, rating);

        // Validações
        validationService.validateRatingRange(rating);
        validationService.validateBookStatusForRating(userId, bookId);
        BookRating existingRating = validationService.validateRatingExists(userId, bookId);

        // Atualiza a avaliação
        Integer oldRating = existingRating.getRating();
        existingRating.setRating(rating);
        BookRating updatedRating = ratingRepository.save(existingRating);
        log.debug("Avaliação atualizada com ID: {}", updatedRating.getId());

        // Atualiza o resumo agregado
        updateSummaryOnUpdate(bookId, oldRating, rating);

        return mapToResponse(updatedRating);
    }

    @Transactional
    public void deleteRating(UUID userId, String bookId) {
        log.info("Removendo avaliação para livro {} do usuário {}", bookId, userId);

        // Validação
        BookRating rating = validationService.validateRatingExists(userId, bookId);

        // Remove a avaliação
        Integer ratingValue = rating.getRating();
        ratingRepository.delete(rating);
        log.debug("Avaliação removida com ID: {}", rating.getId());

        // Atualiza o resumo agregado
        updateSummaryOnDelete(bookId, ratingValue);
    }

    // Busca a avaliação de um usuário específico para um livro.
    @Transactional(readOnly = true)
    public Optional<RatingResponse> getRatingByUser(String bookId, UUID userId) {
        log.debug("Buscando avaliação do usuário {} para o livro {}", userId, bookId);
        return ratingRepository.findByUserIdAndBookId(userId, bookId)
                .map(this::mapToResponse);
    }

    // Busca o resumo agregado de avaliações de um livro.
    @Transactional(readOnly = true)
    public RatingSummaryResponse getRatingSummary(String bookId) {
        log.debug("Buscando resumo de avaliações para o livro {}", bookId);
        
        Optional<BookRatingSummary> summary = ratingSummaryRepository.findByBookId(bookId);
        
        if (summary.isEmpty()) {
            // Retorna resumo vazio se não houver avaliações
            return RatingSummaryResponse.builder()
                    .bookId(bookId)
                    .totalRatings(0)
                    .averageRating(0.0)
                    .build();
        }

        BookRatingSummary summaryEntity = summary.get();
        return RatingSummaryResponse.builder()
                .bookId(summaryEntity.getBookId())
                .totalRatings(summaryEntity.getTotalRatings())
                .averageRating(summaryEntity.getAverageRating())
                .updatedAt(summaryEntity.getUpdatedAt())
                .build();
    }

    // Atualiza o resumo agregado ao criar uma nova avaliação.
    private void updateSummaryOnCreate(String bookId, Integer rating) {
        Optional<BookRatingSummary> summaryOpt = ratingSummaryRepository.findByBookId(bookId);
        
        BookRatingSummary summary = summaryOpt.orElseGet(() -> 
            BookRatingSummary.builder()
                    .bookId(bookId)
                    .totalRatings(0)
                    .ratingSum(0)
                    .averageRating(0.0)
                    .build()
        );

        summary.setTotalRatings(summary.getTotalRatings() + 1);
        summary.setRatingSum(summary.getRatingSum() + rating);
        summary.calculateAverage();

        ratingSummaryRepository.save(summary);
        log.debug("Resumo atualizado para livro {}: total={}, média={}", 
                bookId, summary.getTotalRatings(), summary.getAverageRating());
    }

    // Atualiza o resumo agregado ao editar uma avaliação.
    private void updateSummaryOnUpdate(String bookId, Integer oldRating, Integer newRating) {
        BookRatingSummary summary = ratingSummaryRepository.findByBookId(bookId)
                .orElseThrow(() -> new IllegalStateException(
                        "Resumo não encontrado ao tentar atualizar avaliação para o livro: " + bookId
                ));

        // Ajusta a soma: remove a nota antiga e adiciona a nova
        summary.setRatingSum(summary.getRatingSum() - oldRating + newRating);
        summary.calculateAverage();

        ratingSummaryRepository.save(summary);
        log.debug("Resumo atualizado para livro {}: total={}, média={}", 
                bookId, summary.getTotalRatings(), summary.getAverageRating());
    }

    // Atualiza o resumo agregado ao remover uma avaliação.
    private void updateSummaryOnDelete(String bookId, Integer rating) {
        BookRatingSummary summary = ratingSummaryRepository.findByBookId(bookId)
                .orElseThrow(() -> new IllegalStateException(
                        "Resumo não encontrado ao tentar remover avaliação para o livro: " + bookId
                ));

        summary.setTotalRatings(summary.getTotalRatings() - 1);
        summary.setRatingSum(summary.getRatingSum() - rating);
        summary.calculateAverage();

        // Se não houver mais avaliações, remove o resumo
        if (summary.getTotalRatings() == 0) {
            ratingSummaryRepository.delete(summary);
            log.debug("Resumo removido para livro {} (sem avaliações)", bookId);
        } else {
            ratingSummaryRepository.save(summary);
            log.debug("Resumo atualizado para livro {}: total={}, média={}", 
                    bookId, summary.getTotalRatings(), summary.getAverageRating());
        }
    }

    // Mapeia a entidade BookRating para o DTO RatingResponse.
    private RatingResponse mapToResponse(BookRating rating) {
        return RatingResponse.builder()
                .id(rating.getId())
                .userId(rating.getUserId())
                .bookId(rating.getBookId())
                .rating(rating.getRating())
                .createdAt(rating.getCreatedAt())
                .updatedAt(rating.getUpdatedAt())
                .build();
    }
}

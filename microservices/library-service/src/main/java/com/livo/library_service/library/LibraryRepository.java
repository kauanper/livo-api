package com.livo.library_service.library;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LibraryRepository extends JpaRepository<UserBookEntity, Long> {
    List<UserBookEntity> findAllByUserId(UUID userId);
    List<UserBookEntity> findAllByUserIdAndBookStatus(UUID userId, BookStatus status);
    Optional<UserBookEntity> findByIdAndUserId(Long id, UUID userId);
    boolean existsByUserIdAndBookId(UUID userId, String bookId);
    
    // Query customizada para garantir busca correta
    @Query("SELECT ub FROM UserBookEntity ub WHERE ub.userId = :userId AND ub.bookId = :bookId")
    Optional<UserBookEntity> findByUserIdAndBookId(@Param("userId") UUID userId, @Param("bookId") String bookId);
}

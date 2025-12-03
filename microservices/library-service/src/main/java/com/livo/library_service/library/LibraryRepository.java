package com.livo.library_service.library;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LibraryRepository extends JpaRepository<UserBookEntity, Long> {
    List<UserBookEntity> findAllByUserId(UUID userId);
    List<UserBookEntity> findAllByUserIdAndBookStatus(UUID userId, BookStatus status);
    Optional<UserBookEntity> findByIdAndUserId(Long id, UUID userId);
    boolean existsByUserIdAndBookId(UUID userId, String bookId);
}

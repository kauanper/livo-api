package com.livo.book_service.rating.repositories;

import com.livo.book_service.rating.entities.BookRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<BookRating, UUID> {
    
    Optional<BookRating> findByUserIdAndBookId(UUID userId, String bookId);
    
    boolean existsByUserIdAndBookId(UUID userId, String bookId);
}


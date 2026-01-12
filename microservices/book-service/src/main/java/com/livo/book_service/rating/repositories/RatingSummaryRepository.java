package com.livo.book_service.rating.repositories;

import com.livo.book_service.rating.entities.BookRatingSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RatingSummaryRepository extends JpaRepository<BookRatingSummary, UUID> {
    
    Optional<BookRatingSummary> findByBookId(String bookId);
}


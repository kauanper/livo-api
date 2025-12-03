package com.livo.library_service.shelf.bookShelf;

import com.livo.library_service.shelf.entity.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookShelfRepository extends JpaRepository<BookShelf, UUID> {
    Optional<BookShelf> findByShelfIdAndBookId(UUID shelfId, Long bookId);

    boolean existsByShelfIdAndBookId(UUID shelfId, Long bookId);
    
    List<BookShelf> findByShelfIdAndStatus(UUID shelfId, BookStatus status);
}

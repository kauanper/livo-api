package com.livo.library_service.shelf.bookShelf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookShelfRepository extends JpaRepository<BookShelf, UUID> {
    Optional<BookShelf> findByShelfIdAndBook_id(UUID shelfId, String bookId);

    boolean existsByShelfIdAndBook_id(UUID shelfId, String bookId);
}

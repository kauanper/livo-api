package com.livo.library_service.shelf;

import com.livo.library_service.shelf.bookShelf.BookShelf;
import com.livo.library_service.shelf.entity.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, UUID> {
    List<Shelf> findByUserId(UUID userId);
    
    boolean existsByNameAndUserId(String name, UUID userId);
    
    Optional<Shelf> findByNameAndUserId(String name, UUID userId);

    @Query("""
      SELECT bs
      FROM BookShelf bs
      JOIN UserBookEntity ub ON ub.id = bs.bookId
      WHERE bs.shelf.id = :shelfId
                AND bs.userId = :userId
                AND LOWER(ub.title) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
    """)
    List<BookShelf> searchByShelfAndTitle(UUID shelfId, UUID userId, String searchTerm);

}

package com.livo.library_service.shelf;

import com.livo.library_service.shelf.entity.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, UUID> {
    List<Shelf> findByUserId(UUID userId);
}

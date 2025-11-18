package com.livo.library_service.library;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<UserBookEntity, Long> {
}

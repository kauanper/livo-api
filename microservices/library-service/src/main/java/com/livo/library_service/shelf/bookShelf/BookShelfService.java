package com.livo.library_service.shelf.bookShelf;

import com.livo.library_service.shelf.bookShelf.dto.BookShelfDto;
import com.livo.library_service.shelf.bookShelf.dto.BookShelfPostDto;

import java.util.UUID;

public interface BookShelfService {
    BookShelfDto addBookToShelf(UUID shelfId, BookShelfPostDto postDto, UUID userId);

    void removeBookFromShelf(UUID shelfId, String bookId, UUID userId);
}

package com.livo.library_service.shelf;

import com.livo.library_service.shared.clients.BookClient;
import com.livo.library_service.shared.dtos.book.BookSummaryResponse;
import com.livo.library_service.shared.globalExceptions.custon.BookNotFoundException;
import com.livo.library_service.shelf.bookShelf.dto.BookShelfPostDto;
import com.livo.library_service.shelf.entity.Shelf;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShelfValidate {

    private final ShelfRepository shelfRepository;
    private final BookClient bookClient;

    public Shelf validateShelfOwnership(UUID shelfId, UUID userId) {
        Shelf shelf = shelfRepository.findById(shelfId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shelf not found"));

        if (!shelf.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this shelf");
        }
        return shelf;
    }

    public void validateBooksExist(List<BookShelfPostDto> books) {
        if (books == null || books.isEmpty()) {
            return;
        }
        for (BookShelfPostDto dto : books) {
            try {
                BookSummaryResponse book = bookClient.getBookById(dto.bookId());
                if (book == null) {
                    throw new BookNotFoundException("Book not found with ID: " + dto.bookId());
                }
            } catch (Exception e) {
                throw new BookNotFoundException("Book not found with ID: " + dto.bookId());
            }
        }
    }
}

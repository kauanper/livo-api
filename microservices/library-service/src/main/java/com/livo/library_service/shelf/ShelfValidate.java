package com.livo.library_service.shelf;

import com.livo.library_service.shared.clients.BookClient;
import com.livo.library_service.shared.dtos.book.BookSummaryResponse;
import com.livo.library_service.shared.globalExceptions.custon.BookNotFoundException;
import com.livo.library_service.shelf.entity.Shelf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ShelfValidate {

    @Autowired
    private ShelfRepository shelfRepository;

    @Autowired
    private BookClient bookClient;

    public Shelf validateShelfOwnership(UUID shelfId, UUID userId) {
        Shelf shelf = shelfRepository.findById(shelfId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shelf not found"));

        if (!shelf.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this shelf");
        }
        return shelf;
    }

    public void validateBooksExist(List<Long> bookIds) {
        if (bookIds == null || bookIds.isEmpty()) {
            return;
        }
        for (Long bookId : bookIds) {
            try {
                BookSummaryResponse book = bookClient.getBookById(bookId.toString());
                if (book == null) {
                    throw new BookNotFoundException("Book not found with ID: " + bookId);
                }
            } catch (Exception e) {
                throw new BookNotFoundException("Book not found with ID: " + bookId);
            }
        }
    }
}

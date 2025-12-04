package com.livo.library_service.shelf;

import com.livo.library_service.library.custonExceptions.DeniedAccessException;
import com.livo.library_service.shared.clients.BookClient;
import com.livo.library_service.shared.dtos.book.BookSummaryResponse;
import com.livo.library_service.shared.globalExceptions.custon.BookNotFoundException;
import com.livo.library_service.shared.globalExceptions.custon.ResourceNotFoundException;
import com.livo.library_service.shared.globalExceptions.custon.ShelfNameAlreadyExistsException;
import com.livo.library_service.shelf.bookShelf.dto.BookShelfPostDto;
import com.livo.library_service.shelf.entity.Shelf;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShelfValidate {

    private final ShelfRepository shelfRepository;
    private final BookClient bookClient;

    public Shelf validateShelfOwnership(UUID shelfId, UUID userId) {
        Shelf shelf = shelfRepository
                .findById(shelfId)
                .orElseThrow(() -> new ResourceNotFoundException("Pratileira com o ID " + shelfId + " não não encontrada."));

        if (!shelf.getUserId().equals(userId)) {
            throw new DeniedAccessException("Você não tem permissão para acessar essa prateleira.");
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

    public void validateShelfNameNotExists(String shelfName, UUID userId) {
        if (shelfName != null && shelfRepository.existsByNameAndUserId(shelfName, userId)) {
            throw new ShelfNameAlreadyExistsException(shelfName);
        }
    }

    public void validateShelfNameNotExists(String shelfName, UUID userId, UUID shelfId) {
        if (shelfName != null) {
            Shelf existingShelf = shelfRepository.findByNameAndUserId(shelfName, userId)
                    .orElse(null);
            if (existingShelf != null && !existingShelf.getId().equals(shelfId)) {
                throw new ShelfNameAlreadyExistsException(shelfName);
            }
        }
    }
}

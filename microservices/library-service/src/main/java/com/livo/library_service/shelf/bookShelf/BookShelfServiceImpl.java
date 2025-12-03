package com.livo.library_service.shelf.bookShelf;

import com.livo.library_service.shared.clients.BookClient;
import com.livo.library_service.shared.dtos.book.BookSummaryResponse;
import com.livo.library_service.shared.globalExceptions.custon.BookNotFoundException;
import com.livo.library_service.shelf.ShelfValidate;
import com.livo.library_service.shelf.bookShelf.dto.BookShelfDto;
import com.livo.library_service.shelf.bookShelf.dto.BookShelfPostDto;
import com.livo.library_service.shelf.entity.Shelf;
import com.livo.library_service.shelf.mappers.BookShelfMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BookShelfServiceImpl implements BookShelfService {

    @Autowired
    private BookShelfRepository bookShelfRepository;

    @Autowired
    private ShelfValidate shelfValidate;

    @Autowired
    private BookShelfMapper bookShelfMapper;

    @Autowired
    private BookClient bookClient;

    @Override
    @Transactional
    public BookShelfDto addBookToShelf(UUID shelfId, BookShelfPostDto postDto, UUID userId) {
        // Validate Shelf Ownership
        Shelf shelf = shelfValidate.validateShelfOwnership(shelfId, userId);

        // Validate Book Existence
        try {
            BookSummaryResponse book = bookClient.getBookById(String.valueOf(postDto.bookId()));
            if (book == null) {
                throw new BookNotFoundException("Book not found with ID: " + postDto.bookId());
            }
        } catch (Exception e) {
            throw new BookNotFoundException("Book not found with ID: " + postDto.bookId());
        }

        // Validate Duplication
        if (bookShelfRepository.existsByShelfIdAndBookId(shelfId, postDto.bookId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book already exists in this shelf");
        }

        // Create and Save BookShelf
        BookShelf bookShelf = new BookShelf();
        bookShelf.setBookId(postDto.bookId());
        bookShelf.setUserId(userId);
        bookShelf.setShelf(shelf);
        bookShelf.setStatus(postDto.status());
        bookShelf.setAdded_at(LocalDateTime.now());

        // A classificação inicial é nula ou 0? A entidade possui classificação do tipo Float
        // O DTO não possui classificação, por enquanto vou deixá-lo nulo

        bookShelf = bookShelfRepository.save(bookShelf);

        return bookShelfMapper.toDto(bookShelf);
    }

    @Override
    @Transactional
    public void removeBookFromShelf(UUID shelfId, String bookId, UUID userId) {
        // Validate Shelf Ownership
        shelfValidate.validateShelfOwnership(shelfId, userId);

        // Find BookShelf
        BookShelf bookShelf = bookShelfRepository
                .findByShelfIdAndBookId(shelfId, bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found in this shelf"));

        bookShelfRepository.delete(bookShelf);
    }
}

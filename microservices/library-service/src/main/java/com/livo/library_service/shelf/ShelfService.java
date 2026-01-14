package com.livo.library_service.shelf;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.shelf.bookShelf.BookShelf;
import com.livo.library_service.shelf.bookShelf.BookShelfRepository;
import com.livo.library_service.shelf.bookShelf.dto.BookShelfDto;
import com.livo.library_service.shelf.entity.BookStatus;
import com.livo.library_service.shelf.entity.Shelf;
import com.livo.library_service.shelf.entity.dtos.ShelfDto;
import com.livo.library_service.shelf.entity.dtos.ShelfPostDto;
import com.livo.library_service.shelf.mappers.BookShelfMapper;
import com.livo.library_service.shelf.mappers.ShelfMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShelfService {

    private final ShelfRepository shelfRepository;
    private final ShelfMapper shelfMapper;
    private final ShelfValidate shelfValidate;
    private final BookShelfRepository bookShelfRepository;
    private final BookShelfMapper bookShelfMapper;
    private final LibraryRepository libraryRepository;

    @Transactional
    public ShelfDto save(ShelfPostDto dto, UUID userId) {
        shelfValidate.validateShelfNameNotExists(dto.name(), userId);
        shelfValidate.validateBooksExist(dto.books());

        Shelf shelf = shelfMapper.toEntity(dto);
        shelf.setUserId(userId);

        if (dto.books() != null && !dto.books().isEmpty()) {
            this.extractBooks(shelf, dto, userId);
        } else {
            shelf.setBookShelves(new ArrayList<>());
        }

        shelf = shelfRepository.save(shelf);
        return shelfMapper.toDto(shelf);
    }

    @Transactional(readOnly = true)
    public List<ShelfDto> findAll(UUID userId) {
        List<Shelf> shelves = shelfRepository.findByUserId(userId);

        return shelfMapper.toDtoList(shelves);
    }

    @Transactional(readOnly = true)
    public ShelfDto findById(UUID id, UUID userId) {
        Shelf shelf = shelfValidate.validateShelfOwnership(id, userId);
        return shelfMapper.toDto(shelf);
    }

    @Transactional
    public ShelfDto update(UUID id, ShelfPostDto dto, UUID userId) {
        Shelf shelf = shelfValidate.validateShelfOwnership(id, userId);

        if (dto.name() != null) {
            shelfValidate.validateShelfNameNotExists(dto.name(), userId, id);
            shelf.setName(dto.name());
        }
        if (dto.description() != null)
            shelf.setDescription(dto.description());

        if (dto.books() != null) {
            shelfValidate.validateBooksExist(dto.books());
            // Clear existing books and add new ones? Or append?
            // Usually PUT replaces, PATCH updates. This is PUT in controller.
            // So I will replace the list.

            shelf.getBookShelves().clear();

            this.extractBooks(shelf, dto, userId);

            shelf.getBookShelves().addAll(shelf.getBookShelves());
        }

        shelf = shelfRepository.save(shelf);
        return shelfMapper.toDto(shelf);
    }

    public void delete(UUID id, UUID userId) {
        Shelf shelf = shelfValidate.validateShelfOwnership(id, userId);
        shelfRepository.delete(shelf);
    }

    @Transactional(readOnly = true)
    public List<BookShelfDto> findBookShelvesByStatus(UUID shelfId, BookStatus status, UUID userId) {
        Shelf shelf = shelfValidate.validateShelfOwnership(shelfId, userId);
        List<BookShelf> bookShelves = bookShelfRepository.findByShelfIdAndStatus(shelf.getId(), status);
        return bookShelves.stream()
                .map(bookShelfMapper::toDto)
                .collect(Collectors.toList());
    }

    private void extractBooks(Shelf shelf, ShelfPostDto dto, UUID userId) {
        List<BookShelf> bookShelves = dto.books().stream().map(bookShelfPostDto -> {
            BookShelf bookShelf = new BookShelf();
            bookShelf.setBookId(bookShelfPostDto.id());
            bookShelf.setGoogleBookId(bookShelfPostDto.bookId());
            bookShelf.setUserId(userId);
            bookShelf.setShelf(shelf);
            bookShelf.setStatus(bookShelfPostDto.status());
            bookShelf.setAdded_at(LocalDateTime.now());

            // Busca o thumbnail e title do livro na biblioteca do usuário (UserBookEntity)
            libraryRepository.findById(bookShelfPostDto.id()).ifPresent(userBook -> {
                bookShelf.setThumbnail(userBook.getThumbnail());
                bookShelf.setTitle(userBook.getTitle());
            });

            return bookShelf;
        }).collect(Collectors.toList());
        shelf.setBookShelves(bookShelves);
    }
}

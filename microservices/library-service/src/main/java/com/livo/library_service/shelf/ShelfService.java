package com.livo.library_service.shelf;

import com.livo.library_service.shelf.bookShelf.BookShelf;
import com.livo.library_service.shelf.entity.BookStatus;
import com.livo.library_service.shelf.entity.Shelf;
import com.livo.library_service.shelf.entity.dtos.ShelfDto;
import com.livo.library_service.shelf.entity.dtos.ShelfPostDto;
import com.livo.library_service.shelf.mappers.ShelfMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShelfService {

    @Autowired
    private ShelfRepository shelfRepository;

    @Autowired
    private ShelfMapper shelfMapper;

    @Autowired
    private ShelfValidate shelfValidate;

    @Transactional
    public ShelfDto save(ShelfPostDto dto, UUID userId) {
        shelfValidate.validateBooksExist(dto.books());

        Shelf shelf = shelfMapper.toEntity(dto);
        shelf.setUserId(userId);

        final Shelf finalShelf = shelf;

        if (dto.books() != null && !dto.books().isEmpty()) {
            List<BookShelf> bookShelves = dto.books().stream().map(bookId -> {
                BookShelf bookShelf = new BookShelf();
                bookShelf.setBookId(bookId.toString());
                bookShelf.setUserId(userId);
                bookShelf.setShelf(finalShelf);
                bookShelf.setStatus(BookStatus.QUERO_LER); // Default status
                bookShelf.setAdded_at(LocalDateTime.now());
                return bookShelf;
            }).collect(Collectors.toList());
            shelf.setBookShelves(bookShelves);
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

    public ShelfDto findById(UUID id, UUID userId) {
        Shelf shelf = shelfValidate.validateShelfOwnership(id, userId);
        return shelfMapper.toDto(shelf);
    }

    @Transactional
    public ShelfDto update(UUID id, ShelfPostDto dto, UUID userId) {
        Shelf shelf = shelfValidate.validateShelfOwnership(id, userId);

        if (dto.name() != null)
            shelf.setName(dto.name());
        if (dto.description() != null)
            shelf.setDescription(dto.description());

        if (dto.books() != null) {
            shelfValidate.validateBooksExist(dto.books());
            // Clear existing books and add new ones? Or append?
            // Usually PUT replaces, PATCH updates. This is PUT in controller.
            // So I will replace the list.

            shelf.getBookShelves().clear();

            final Shelf finalShelf = shelf;

            List<BookShelf> newBooks = dto.books().stream().map(bookId -> {
                BookShelf bookShelf = new BookShelf();
                bookShelf.setBookId(bookId.toString());
                bookShelf.setUserId(userId);
                bookShelf.setShelf(finalShelf);
                bookShelf.setStatus(BookStatus.QUERO_LER);
                bookShelf.setAdded_at(LocalDateTime.now());
                return bookShelf;
            }).toList();

            shelf.getBookShelves().addAll(newBooks);
        }

        shelf = shelfRepository.save(shelf);
        return shelfMapper.toDto(shelf);
    }

    public void delete(UUID id, UUID userId) {
        Shelf shelf = shelfValidate.validateShelfOwnership(id, userId);
        shelfRepository.delete(shelf);
    }
}

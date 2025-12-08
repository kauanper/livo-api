package com.livo.library_service.shelf;

import com.livo.library_service.library.dtos.association.AssociationResponseDTO;
import com.livo.library_service.search_book.SearchBookUseCase;
import com.livo.library_service.search_book.strategies.SearchRequest;
import com.livo.library_service.search_book.strategies.SearchType;
import com.livo.library_service.shared.notations.CurrentUser;
import com.livo.library_service.shelf.entity.BookStatus;
import com.livo.library_service.shelf.entity.dtos.ShelfDto;
import com.livo.library_service.shelf.entity.dtos.ShelfPostDto;
import com.livo.library_service.shelf.bookShelf.BookShelfService;
import com.livo.library_service.shelf.bookShelf.dto.BookShelfDto;
import com.livo.library_service.shelf.bookShelf.dto.BookShelfPostDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/library/shelfs")
@RequiredArgsConstructor
public class ShelfController {

    private final ShelfService shelfService;
    private final BookShelfService bookShelfService;
    private final SearchBookUseCase searchBookUseCase;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShelfDto> register(
            @Valid @RequestBody ShelfPostDto postDto,
            @CurrentUser UUID userId) {
        ShelfDto dto = shelfService.save(postDto, userId);
        URI uri = URI.create("/shelfs/" + dto.id());
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ShelfDto>> getAll(@CurrentUser UUID userId) {
        List<ShelfDto> shelfs = shelfService.findAll(userId);
        return ResponseEntity.ok().body(shelfs);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ShelfDto> getbyId(@PathVariable("id") UUID id, @CurrentUser UUID userId) {
        ShelfDto shelf = shelfService.findById(id, userId);
        return ResponseEntity.ok().body(shelf);
    }

    @GetMapping("/{id}/books")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookShelfDto>> getBooksByStatus(
            @PathVariable("id") UUID id,
            @RequestParam("status") BookStatus status,
            @CurrentUser UUID userId) {
        List<BookShelfDto> bookShelves = shelfService.findBookShelvesByStatus(id, status, userId);
        return ResponseEntity.ok().body(bookShelves);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ShelfDto> update(
            @PathVariable("id") UUID id,
            @Valid @RequestBody ShelfPostDto postDto,
            @CurrentUser UUID userId) {
        ShelfDto dto = shelfService.update(id, postDto, userId);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id, @CurrentUser UUID userId) {
        shelfService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/books")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookShelfDto> addBook(
            @PathVariable("id") UUID id,
            @Valid @RequestBody BookShelfPostDto postDto,
            @CurrentUser UUID userId) {
        BookShelfDto dto = bookShelfService.addBookToShelf(id, postDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping("/{id}/books/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> removeBook(
            @PathVariable("id") UUID id,
            @PathVariable("bookId") Long bookId,
            @CurrentUser UUID userId) {
        bookShelfService.removeBookFromShelf(id, bookId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{shelfId}/search/{term}")
    public ResponseEntity<List<AssociationResponseDTO>> searchBooks(@CurrentUser UUID userId,
                                                                    @PathVariable UUID shelfId,
                                                                    @PathVariable String term) {
        List<AssociationResponseDTO> books = searchBookUseCase.execute(new SearchRequest(userId, term, shelfId, SearchType.TITLE_SHELVES));
        return ResponseEntity.ok(books);
    }
}

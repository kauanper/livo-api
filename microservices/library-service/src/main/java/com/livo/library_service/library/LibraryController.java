package com.livo.library_service.library;

import com.livo.library_service.library.dtos.BookStatusPatchDTO;
import com.livo.library_service.library.dtos.association.AssociationRegisterDTO;
import com.livo.library_service.library.dtos.association.AssociationResponseDTO;
import com.livo.library_service.library.dtos.book_count.BookCountResponse;
import com.livo.library_service.library.dtos.book_count.BookIdResponse;
import com.livo.library_service.library.services.*;
import com.livo.library_service.search_book.SearchBookUseCase;
import com.livo.library_service.shared.notations.CurrentUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/library")
public class LibraryController {
    @Autowired
    private CreateAssociationUseCase createAssociationUseCase;

    @Autowired
    private DeleteByIdUseCase deleteByIdUseCase;

    @Autowired
    private ListLibraryBooksUseCase listLibraryBooksUseCase;

    @Autowired
    private SearchBookUseCase searchBookUseCase;

    @Autowired
    private PatchAssociationUseCase patchAssociationUseCase;

    @Autowired
    private BookCountUseCase bookCountUseCase;

    @Autowired
    private ListBooksIdUseCase listBooksIdUseCase;


    @PostMapping
    public ResponseEntity<AssociationResponseDTO> save(@RequestBody @Valid AssociationRegisterDTO dto,
                                                       @CurrentUser UUID currentUser){
        AssociationResponseDTO response = createAssociationUseCase.execute(dto, currentUser);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userBookId}")
    public ResponseEntity<String> deleteById(@PathVariable Long userBookId) {
        deleteByIdUseCase.execute(userBookId);
        return ResponseEntity.ok("Associação removida com sucesso.");
    }


    @GetMapping
    public ResponseEntity<List<AssociationResponseDTO>> getLibraryByUserId(@CurrentUser UUID userId,
                                                                           @RequestParam(required = false) @Valid BookStatus status) {
        List<AssociationResponseDTO> books = listLibraryBooksUseCase.execute(userId, Optional.ofNullable(status));
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search/{term}")
    public ResponseEntity<List<AssociationResponseDTO>> searchBooks(@CurrentUser UUID userId,
                                                                    @PathVariable String term) {
        List<AssociationResponseDTO> books = searchBookUseCase.execute(userId, term);
        return ResponseEntity.ok(books);
    }

    @PatchMapping("/{userBookId}")
    public ResponseEntity<AssociationResponseDTO> patchByBookStatus(@CurrentUser UUID userId,
                                                                    @PathVariable Long userBookId,
                                                                    @RequestBody BookStatusPatchDTO dto){
        AssociationResponseDTO book = patchAssociationUseCase.execute(userId, userBookId, dto);
        return ResponseEntity.ok(book);
    }

    //-------------------------------ROTAS INTERNAS
    @GetMapping("/internal/book-count/{userId}")
    public ResponseEntity<BookCountResponse> getBookCount(@PathVariable UUID userId) {
        BookCountResponse response = bookCountUseCase.execute(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/internal/booksId/{userId}")
    public ResponseEntity<List<BookIdResponse>> getId(@PathVariable UUID userId){
        List<BookIdResponse> response = listBooksIdUseCase.execute(userId);
        return ResponseEntity.ok(response);
    }
}

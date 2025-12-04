package com.livo.library_service.library.services;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.custonExceptions.ExistingAssociationException;
import com.livo.library_service.library.dtos.association.AssociationRegisterDTO;
import com.livo.library_service.library.dtos.association.AssociationResponseDTO;
import com.livo.library_service.shared.dtos.book.BookSummaryResponse;
import com.livo.library_service.library.mappers.AssociationMappers;
import com.livo.library_service.shared.clients.BookClient;
import com.livo.library_service.shared.clients.UserClient;
import com.livo.library_service.shared.globalExceptions.custon.BookNotFoundException;
import com.livo.library_service.shared.globalExceptions.custon.UserNotFoundException;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateAssociationUseCase {

    private final LibraryRepository libraryRepository;
    private final AssociationMappers associationMappers;
    private final UserClient userClient;
    private final BookClient bookClient;

    @Transactional
    public AssociationResponseDTO execute(AssociationRegisterDTO register, UUID userID) {

        try {
            userClient.getById(userID);
        } catch (FeignException.NotFound ex) {
            throw new UserNotFoundException(userID);
        }

        BookSummaryResponse bookDto;
        try {
            bookDto = bookClient.getBookById(register.bookId());
        } catch (FeignException.NotFound ex) {
            throw new BookNotFoundException(register.bookId());
        }

        boolean exists = libraryRepository.existsByUserIdAndBookId(userID, register.bookId());
        if (exists) {
            throw new ExistingAssociationException("biblioteca");
        }

        UserBookEntity entity = AssociationMappers.toEntity(register, bookDto,userID);
        UserBookEntity saved = libraryRepository.save(entity);

        return AssociationMappers.toResponseDTO(saved);
    }
}

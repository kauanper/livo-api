package com.livo.library_service.library.services;

import com.livo.library_service.library.BookStatus;
import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.custonExceptions.EmptyPersonalLibraryException;
import com.livo.library_service.library.dtos.association.AssociationResponseDTO;
import com.livo.library_service.library.mappers.AssociationMappers;
import com.livo.library_service.shared.clients.UserClient;
import com.livo.library_service.shared.globalExceptions.custon.UserNotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListLibraryBooksUseCase {

    private final LibraryRepository libraryRepository;
    private final UserClient userClient;

    public List<AssociationResponseDTO> execute(UUID userId, Optional<BookStatus> status) {

        try {
            userClient.getById(userId);
        } catch (FeignException.NotFound e) {
            throw new UserNotFoundException(userId);
        }

        List<UserBookEntity> entities;
        if (status.isEmpty())
            entities = libraryRepository.findAllByUserId(userId);
        else
            entities = libraryRepository.findAllByUserIdAndBookStatus(userId, status.get());

        if (entities.isEmpty()) {
            throw new EmptyPersonalLibraryException();
        }

        return entities.stream()
                .map(AssociationMappers::toResponseDTO)
                .collect(Collectors.toList());
    }
}

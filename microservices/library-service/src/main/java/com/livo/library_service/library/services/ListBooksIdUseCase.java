package com.livo.library_service.library.services;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.dtos.book_count.BookIdResponse;
import com.livo.library_service.reeding_register.services.CalculateProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListBooksIdUseCase {

    private final LibraryRepository libraryRepository;
    private final CalculateProgressService calculateProgressService;

    public List<BookIdResponse> execute(UUID userId){
        return libraryRepository.findAllByUserId(userId)
                .stream()
                .map(entity -> new BookIdResponse(
                        entity.getBookId(),
                        entity.getBookStatus(),
                        entity.getId(),
                        calculateProgressService
                                .getReadingProgressByLibraryBookId(
                                        entity.getId(),
                                        entity.getBookId(),
                                        entity.getUserId()
                                )
                                .intValue(),
                        entity.getPersonalRatting(),
                        null //obs: generalRatting não existe na entidade ainda
                ))
                .toList();
    }
}


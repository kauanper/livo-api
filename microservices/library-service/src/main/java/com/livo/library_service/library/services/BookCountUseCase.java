package com.livo.library_service.library.services;

import com.livo.library_service.library.BookStatus;
import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.dtos.book_count.BookCountResponse;
import com.livo.library_service.shared.clients.UserClient;
import com.livo.library_service.shared.globalExceptions.custon.UserNotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookCountUseCase {

    private final LibraryRepository libraryRepository;

    public BookCountResponse execute(UUID userId) {


        List<UserBookEntity> books;
        int[] counts = new int[BookStatus.values().length + 1];


        for (int i = 0; i < BookStatus.values().length; i++) {
            BookStatus status = BookStatus.values()[i];
            books = libraryRepository.findAllByUserIdAndBookStatus(userId, status);
            counts[i] = books.size();
            counts[BookStatus.values().length] += counts[i];
            books.clear();
        }

        return new BookCountResponse(
                counts[0], //QUERO_LER
                counts[1], //LENDO
                counts[2], //LIDO
                counts[3], //ABANDONADO
                counts[4]  //TOTAL
        );
    }
}

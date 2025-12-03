package com.livo.library_service.library.services;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.dtos.book_count.BookIdResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Library;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListBooksIdUseCase {

    private final LibraryRepository libraryRepository;

    public List<BookIdResponse> execute(UUID userId){
        return libraryRepository.findAllByUserId(userId)
                .stream()
                .map(entity -> new BookIdResponse(entity.getBookId().toString()))
                .toList();
    }
}

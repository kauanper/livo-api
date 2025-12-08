package com.livo.library_service.search_book.strategies;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.dtos.association.AssociationResponseDTO;
import com.livo.library_service.library.mappers.AssociationMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TitleLibrarySearchStrategy implements SearchStrategy {

    private final LibraryRepository libraryRepository;

    @Override
    public List<AssociationResponseDTO> search(UUID userId, String searchTerm) {

        return libraryRepository.findAllByUserId(userId)
                .stream()
                .filter(book -> book.getTitle() != null &&
                        book.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
                .map(AssociationMappers::toResponseDTO)
                .toList();
    }
}

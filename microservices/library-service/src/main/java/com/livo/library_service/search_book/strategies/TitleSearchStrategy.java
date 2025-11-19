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
public class TitleSearchStrategy implements SearchStrategy {

    private final LibraryRepository libraryRepository;
    private final AssociationMappers associationMappers;

    @Override
    public List<AssociationResponseDTO> search(UUID userId, String searchTerm) {
        List<UserBookEntity> userBooks = libraryRepository.findAllByUserId(userId);

        return userBooks.stream()
                .filter(b -> b.getTitle() != null && b.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
                .map(AssociationMappers::toResponseDTO)
                .collect(Collectors.toList());

    }
}

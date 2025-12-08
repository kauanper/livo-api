package com.livo.library_service.search_book;

import com.livo.library_service.library.dtos.association.AssociationResponseDTO;
import com.livo.library_service.search_book.strategies.SearchStrategy;
import com.livo.library_service.search_book.strategies.SearchStrategySelector;
import com.livo.library_service.search_book.strategies.SearchType;
import com.livo.library_service.shared.clients.UserClient;
import com.livo.library_service.shared.globalExceptions.custon.UserNotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SearchBookUseCase {

    private final UserClient userClient;
    private final SearchStrategySelector strategySelector;

    public List<AssociationResponseDTO> execute(UUID userId, String searchTerm, SearchType type) {

        this.validateUser(userId);

        SearchStrategy strategy = this.strategySelector.get(type);

        return strategy.search(userId, searchTerm);
    }

    private void validateUser(UUID userId) {
        try {
            userClient.getById(userId);
        } catch (FeignException.NotFound ex) {
            throw new UserNotFoundException(userId);
        }
    }
}

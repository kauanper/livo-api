package com.livo.book_service.services.search.q_strategies;

import com.livo.book_service.exceptions.custom.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchStrategyFactory {

    private final Map<String, SearchStrategy> strategies;

    public SearchStrategy get(String type) {
        SearchStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new InvalidRequestException("Tipo de pesquisa inválido: " + type);
        }
        return strategy;
    }
}


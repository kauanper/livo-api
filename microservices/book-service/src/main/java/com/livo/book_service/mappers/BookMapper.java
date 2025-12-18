package com.livo.book_service.mappers;

import com.livo.book_service.dtos.BookResponse;
import com.livo.book_service.dtos.BookSummaryResponse;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookSummaryResponse toSummary(BookResponse.BookItem item) {
        if (item == null || item.getVolumeInfo() == null) {
            return null;
        }

        var info = item.getVolumeInfo();

        String thumbnail = (info.getImageLinks() != null)
                ? info.getImageLinks().getThumbnail()
                : null;

        return new BookSummaryResponse(
                item.getId(),
                info.getTitle(),
                info.getAuthors(),
                info.getPublisher(),
                info.getPublishedDate(),
                info.getPageCount(),
                info.getAverageRating(),
                info.getRatingsCount(),
                thumbnail,
                info.getLanguage(),
                info.getDescription(),
                false,
                null
        );

    }
}

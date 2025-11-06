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
                item.getId(),                      // id
                info.getTitle(),                   // title
                info.getAuthors(),                 // authors
                info.getPublisher(),               // publisher
                info.getPublishedDate(),           // publishedDate
                info.getPageCount(),               // pageCount
                info.getAverageRating(),           // averageRating
                info.getRatingsCount(),            // ratingsCount
                thumbnail                          // thumbnail
        );
    }
}

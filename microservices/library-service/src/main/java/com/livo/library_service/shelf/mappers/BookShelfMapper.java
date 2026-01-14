package com.livo.library_service.shelf.mappers;

import com.livo.library_service.shelf.bookShelf.BookShelf;
import com.livo.library_service.shelf.bookShelf.dto.BookShelfDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class BookShelfMapper {

    // abstract BookShelf toEntity(BookShelfDto dto);
    // abstract BookShelfDto toDto(BookShelf entity);

    public BookShelf toEntity(BookShelfDto dto) {
        if (dto == null) {
            return null;
        }

        BookShelf bookShelf = new BookShelf();

        bookShelf.setId(dto.id());
        bookShelf.setBookId(dto.bookId());
        bookShelf.setGoogleBookId(dto.googleBookId());
        bookShelf.setStatus(dto.status());
        bookShelf.setThumbnail(dto.thumbnail());
        bookShelf.setTitle(dto.title());
        bookShelf.setAdded_at(dto.addedAt());
        bookShelf.setRating(dto.rating());

        return bookShelf;
    }

    public BookShelfDto toDto(BookShelf entity) {
        if (entity == null) {
            return null;
        }

        return new BookShelfDto(
                entity.getId(),
                entity.getBookId(),
                entity.getGoogleBookId(),
                entity.getStatus(),
                entity.getThumbnail(),
                entity.getTitle(),
                entity.getAdded_at(),
                entity.getRating());
    }
}

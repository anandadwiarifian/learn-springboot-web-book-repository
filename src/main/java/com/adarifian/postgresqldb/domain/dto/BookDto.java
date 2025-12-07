package com.adarifian.postgresqldb.domain.dto;

import com.adarifian.postgresqldb.domain.entities.BookEntity;

public record BookDto(String isbn, String title, AuthorDto author) {
    public static BookDto from(BookEntity bookEntity) {
        if (bookEntity == null) {
            return null;
        }
        return new BookDto(bookEntity.getIsbn(), bookEntity.getTitle(),
                AuthorDto.from(bookEntity.getAuthor()));
    }

    public BookEntity toEntity() {
        return BookEntity.builder().isbn(isbn()).title(title())
                .author(author() != null ? author().toEntity() : null).build();
    }

}

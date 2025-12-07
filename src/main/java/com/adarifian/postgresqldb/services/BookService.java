package com.adarifian.postgresqldb.services;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.adarifian.postgresqldb.domain.entities.BookEntity;

public interface BookService {
    BookEntity save(String isbn, BookEntity bookEntity);

    Iterable<BookEntity> getBooks();

    Page<BookEntity> getBooks(Pageable pageable);

    Optional<BookEntity> getBook(String isbn);

    Boolean existsById(String isbn);

    BookEntity partialUpdate(String isbn, BookEntity bookEntity);

    void deleteBook(String isbn);
}

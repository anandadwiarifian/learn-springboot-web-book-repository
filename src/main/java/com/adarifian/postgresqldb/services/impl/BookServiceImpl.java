package com.adarifian.postgresqldb.services.impl;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adarifian.postgresqldb.domain.entities.BookEntity;
import com.adarifian.postgresqldb.repositories.BookRepository;
import com.adarifian.postgresqldb.services.BookService;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity save(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    @Override
    public Iterable<BookEntity> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Page<BookEntity> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Optional<BookEntity> getBook(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public Boolean existsById(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public BookEntity partialUpdate(String isbn, BookEntity bookEntity) {
        return bookRepository.findById(isbn).map(existingBook -> {
            Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);

            // in real world scenario, update author should only be done in PATCH author
            // but this is for learning purposes
            Optional.ofNullable(bookEntity.getAuthor()).ifPresent(bookEntityAuthor -> {
                // update author attributes
                // option 1: if
                if (existingBook.getAuthor() == null) {
                    existingBook.setAuthor(bookEntityAuthor);
                    return;
                }
                Optional.ofNullable(bookEntityAuthor.getName())
                        .ifPresent(existingBook.getAuthor()::setName);
                Optional.ofNullable(bookEntityAuthor.getAge())
                        .ifPresent(existingBook.getAuthor()::setAge);

                // option 2: OfNullable wrap
                // Optional.ofNullable(existingBook.getAuthor()).ifPresentOrElse(
                // existingBookAuthor -> {
                // Optional.ofNullable(bookEntityAuthor.getName()).ifPresent(existingBookAuthor::setName);
                // Optional.ofNullable(bookEntityAuthor.getAge()).ifPresent(existingBookAuthor::setAge);
                // },
                // () -> existingBook.setAuthor(bookEntityAuthor));

            });

            return bookRepository.save(existingBook);
        }).orElseThrow(() -> new RuntimeException("Book doesn't exist"));
    }

    @Override
    public void deleteBook(String isbn) {
        bookRepository.deleteById(isbn);
    }
}

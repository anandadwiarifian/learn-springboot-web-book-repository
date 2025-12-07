package com.adarifian.postgresqldb.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.adarifian.postgresqldb.domain.dto.BookDto;
import com.adarifian.postgresqldb.domain.entities.BookEntity;
import com.adarifian.postgresqldb.services.BookService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable String isbn,
            @RequestBody BookDto bookDto) {
        Boolean existsBefore = bookService.existsById(isbn);

        BookEntity bookEntity = bookService.save(isbn, bookDto.toEntity());
        return new ResponseEntity<>(BookDto.from(bookEntity),
                existsBefore ? HttpStatus.OK : HttpStatus.CREATED);
    }

    @GetMapping("/books")
    public Page<BookDto> getBooks(Pageable pageable) {
        Page<BookEntity> books = bookService.getBooks(pageable);

        return books.map(BookDto::from);
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable String isbn) {
        return bookService.getBook(isbn).map(BookDto::from).map(record -> ResponseEntity.ok(record))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(@PathVariable String isbn,
            @RequestBody BookDto bookDto) {
        if (!bookService.existsById(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BookEntity updatedBook = bookService.partialUpdate(isbn, bookDto.toEntity());
        return ResponseEntity.ok(BookDto.from(updatedBook));
    }

    @DeleteMapping("books/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable String isbn) {
        if (!bookService.existsById(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        bookService.deleteBook(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

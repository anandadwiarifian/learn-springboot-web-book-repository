package com.adarifian.postgresqldb.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.adarifian.postgresqldb.domain.dto.AuthorDto;
import com.adarifian.postgresqldb.domain.entities.AuthorEntity;
import com.adarifian.postgresqldb.services.AuthorService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        AuthorEntity savedAuthorEntity = authorService.save(authorDto.toEntity());
        return new ResponseEntity<>(AuthorDto.from(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping("/authors")
    public Page<AuthorDto> getAuthors(Pageable pageable) {
        Page<AuthorEntity> authors = authorService.getAuthors(pageable);
        return authors.map(AuthorDto::from);
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable Long id) {
        return authorService.getAuthor(id).map(AuthorDto::from)
                .map(record -> ResponseEntity.ok(record))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("authors/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id,
            @RequestBody AuthorDto authorDto) {
        if (!authorService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AuthorEntity updatedAuthor = authorService.save(authorDto.withId(id).toEntity());
        return ResponseEntity.ok(AuthorDto.from(updatedAuthor));
    }

    @PatchMapping("authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdateAuthor(@PathVariable Long id,
            @RequestBody AuthorDto authorDto) {
        if (!authorService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AuthorEntity updatedAuthor = authorService.partialUpdate(id, authorDto.toEntity());
        return ResponseEntity.ok(AuthorDto.from(updatedAuthor));
    }

    @DeleteMapping("authors/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        if (!authorService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

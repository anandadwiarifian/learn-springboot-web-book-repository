package com.adarifian.postgresqldb.services.impl;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adarifian.postgresqldb.domain.entities.AuthorEntity;
import com.adarifian.postgresqldb.repositories.AuthorRepository;
import com.adarifian.postgresqldb.services.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public Iterable<AuthorEntity> getAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Page<AuthorEntity> getAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Override
    public Optional<AuthorEntity> getAuthor(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {
        return authorRepository.findById(id).map(existingAuthor -> {
            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);

            return authorRepository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("Author doesn't exist"));
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}

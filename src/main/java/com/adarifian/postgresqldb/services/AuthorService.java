package com.adarifian.postgresqldb.services;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.adarifian.postgresqldb.domain.entities.AuthorEntity;

public interface AuthorService {
    AuthorEntity save(AuthorEntity authorEntity);

    Iterable<AuthorEntity> getAuthors();

    Page<AuthorEntity> getAuthors(Pageable pageable);

    Optional<AuthorEntity> getAuthor(Long id);

    Boolean existsById(Long id);

    AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity);

    void deleteAuthor(Long id);
}

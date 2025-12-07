package com.adarifian.postgresqldb.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.adarifian.postgresqldb.domain.entities.AuthorEntity;

@Repository
public interface AuthorRepository
        extends CrudRepository<AuthorEntity, Long>, PagingAndSortingRepository<AuthorEntity, Long> {

    public Iterable<AuthorEntity> findByAgeLessThan(int age);
}

package com.adarifian.postgresqldb.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.adarifian.postgresqldb.domain.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    public Iterable<Author> findByAgeLessThan(int age);

    // for learning purposes. JPA can create the query for you based on
    // the "LessThanEqual" keyword
    @Query("SELECT a FROM Author a WHERE a.age <= ?1")
    public Iterable<Author> findAgeLessThanEqual(int age);
}

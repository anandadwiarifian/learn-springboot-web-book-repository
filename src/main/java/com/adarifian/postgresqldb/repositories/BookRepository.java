package com.adarifian.postgresqldb.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.adarifian.postgresqldb.domain.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {

}

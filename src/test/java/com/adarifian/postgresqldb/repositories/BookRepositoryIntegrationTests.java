package com.adarifian.postgresqldb.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.IterableUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.adarifian.postgresqldb.TestDataUtil;
import com.adarifian.postgresqldb.domain.entities.AuthorEntity;
import com.adarifian.postgresqldb.domain.entities.BookEntity;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {

    private BookRepository underTest;
    private AuthorRepository authorRepository;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository underTest,
            AuthorRepository authorRepository) {
        this.underTest = underTest;
        this.authorRepository = authorRepository;
    }

    @Test
    public void testBookCanBeCreatedAndFound() {
        AuthorEntity author = TestDataUtil.createTestAuthorB();
        authorRepository.save(author);

        BookEntity book = TestDataUtil.createTestBookA(author);
        underTest.save(book);

        var result = underTest.findById(book.getIsbn());
        assertEquals(result.isPresent(), true);
        assertEquals(result.get(), book);
    }

    @Test
    public void testMultipleBooksCanBeCreatedAndFound() {
        AuthorEntity authorE = TestDataUtil.createTestAuthorE();
        AuthorEntity authorF = TestDataUtil.createTestAuthorF();
        authorRepository.saveAll(List.of(authorE, authorF));

        BookEntity bookB = TestDataUtil.createTestBookB(authorE);
        BookEntity bookC = TestDataUtil.createTestBookC(authorF);
        underTest.saveAll(List.of(bookB, bookC));

        Collection<BookEntity> results = IterableUtil.toCollection(underTest.findAll());

        assertEquals(results.size(), 2);
        assertEquals(results.contains(bookB), true);
        assertEquals(results.contains(bookC), true);
    }

    @Test
    public void testUpdateBook() {
        AuthorEntity author = TestDataUtil.createTestAuthorB();
        authorRepository.save(author);
        BookEntity book = TestDataUtil.createTestBookA(author);
        underTest.save(book);

        book.setTitle("The Hobbit");
        underTest.save(book);

        Optional<BookEntity> result = underTest.findById(book.getIsbn());
        assertEquals(result.get(), book);
    }

    @Test
    public void testDeleteBook() {
        AuthorEntity authorB = TestDataUtil.createTestAuthorB();
        authorRepository.save(authorB);
        BookEntity book = TestDataUtil.createTestBookA(authorB);
        underTest.save(book);

        underTest.deleteById(book.getIsbn());

        Optional<BookEntity> result = underTest.findById(book.getIsbn());
        assertEquals(result.isEmpty(), true);
    }
}

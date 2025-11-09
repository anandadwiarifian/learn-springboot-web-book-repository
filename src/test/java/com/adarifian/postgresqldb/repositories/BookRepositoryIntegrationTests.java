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
import com.adarifian.postgresqldb.domain.Author;
import com.adarifian.postgresqldb.domain.Book;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {

    private BookRepository underTest;
    private AuthorRepository authorRepository;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository underTest, AuthorRepository authorRepository) {
        this.underTest = underTest;
        this.authorRepository = authorRepository;
    }

    @Test
    public void testBookCanBeCreatedAndFound() {
        Author author = TestDataUtil.createTestAuthorB();
        authorRepository.save(author);

        Book book = TestDataUtil.createTestBookA(author);
        underTest.save(book);

        var result = underTest.findById(book.getIsbn());
        assertEquals(result.isPresent(), true);
        assertEquals(result.get(), book);
    }

    @Test
    public void testMultipleBooksCanBeCreatedAndFound() {
        Author authorE = TestDataUtil.createTestAuthorE();
        Author authorF = TestDataUtil.createTestAuthorF();
        authorRepository.saveAll(List.of(authorE, authorF));

        Book bookB = TestDataUtil.createTestBookB(authorE);
        Book bookC = TestDataUtil.createTestBookC(authorF);
        underTest.saveAll(List.of(bookB, bookC));

        Collection<Book> results = IterableUtil.toCollection(underTest.findAll());

        assertEquals(results.size(), 2);
        assertEquals(results.contains(bookB), true);
        assertEquals(results.contains(bookC), true);
    }

    @Test
    public void testUpdateBook() {
        Author author = TestDataUtil.createTestAuthorB();
        authorRepository.save(author);
        Book book = TestDataUtil.createTestBookA(author);
        underTest.save(book);

        book.setTitle("The Hobbit");
        underTest.save(book);

        Optional<Book> result = underTest.findById(book.getIsbn());
        assertEquals(result.get(), book);
    }

    @Test
    public void testDeleteBook() {
        Author authorB = TestDataUtil.createTestAuthorB();
        authorRepository.save(authorB);
        Book book = TestDataUtil.createTestBookA(authorB);
        underTest.save(book);

        underTest.deleteById(book.getIsbn());

        Optional<Book> result = underTest.findById(book.getIsbn());
        assertEquals(result.isEmpty(), true);
    }
}

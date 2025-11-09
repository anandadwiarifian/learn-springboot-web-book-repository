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

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTests {

    private AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testAuthorCanBeCreatedAndFound() {
        Author author = TestDataUtil.createTestAuthorA();
        underTest.save(author);
        Optional<Author> results = underTest.findById(author.getId());

        assertEquals(results.isPresent(), true);
        assertEquals(author, results.get());
    }

    @Test
    public void testMultipleAuthorsCanBeCreatedAndFound() {
        Author authorC = TestDataUtil.createTestAuthorC();
        Author authorD = TestDataUtil.createTestAuthorD();

        underTest.saveAll(List.of(authorC, authorD));

        Collection<Author> results = IterableUtil.toCollection(underTest.findAll());

        assertEquals(results.size(), 2);
        assertEquals(results.contains(authorC), true);
        assertEquals(results.contains(authorD), true);
    }

    @Test
    public void testUpdateAuthor() {
        Author author = TestDataUtil.createTestAuthorA();
        underTest.save(author);

        author.setName("James May");
        underTest.save(author);

        Optional<Author> result = underTest.findById(author.getId());
        assertEquals(result.get(), author);
    }

    @Test
    public void testDeleteAuthor() {
        Author author = TestDataUtil.createTestAuthorA();
        underTest.save(author);

        underTest.deleteById(author.getId());

        Optional<Author> result = underTest.findById(author.getId());
        assertEquals(result.isEmpty(), true);
    }

    @Test
    public void testGetAuthorsWithAgeLessThan() {
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorE = TestDataUtil.createTestAuthorE();
        Author authorF = TestDataUtil.createTestAuthorF();
        underTest.saveAll(List.of(authorB, authorE, authorF));

        Collection<Author> results = IterableUtil.toCollection(underTest.findByAgeLessThan(75));
        assertEquals(results.size(), 2);
        assertEquals(results.contains(authorE), true);
        assertEquals(results.contains(authorF), true);
    }

    @Test
    public void testGetAuthorsWithAgeLessThanEqual() {
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorE = TestDataUtil.createTestAuthorE();
        Author authorF = TestDataUtil.createTestAuthorF();
        underTest.saveAll(List.of(authorB, authorE, authorF));

        Collection<Author> results = IterableUtil.toCollection(underTest.findAgeLessThanEqual(70));
        assertEquals(results.size(), 2);
        assertEquals(results.contains(authorE), true);
        assertEquals(results.contains(authorF), true);
    }

}

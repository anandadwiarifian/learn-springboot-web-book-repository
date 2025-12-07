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
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        underTest.save(author);
        Optional<AuthorEntity> results = underTest.findById(author.getId());

        assertEquals(results.isPresent(), true);
        assertEquals(author, results.get());
    }

    @Test
    public void testMultipleAuthorsCanBeCreatedAndFound() {
        AuthorEntity authorC = TestDataUtil.createTestAuthorC();
        AuthorEntity authorD = TestDataUtil.createTestAuthorD();

        underTest.saveAll(List.of(authorC, authorD));

        Collection<AuthorEntity> results = IterableUtil.toCollection(underTest.findAll());

        assertEquals(results.size(), 2);
        assertEquals(results.contains(authorC), true);
        assertEquals(results.contains(authorD), true);
    }

    @Test
    public void testUpdateAuthor() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        underTest.save(author);

        author.setName("James May");
        underTest.save(author);

        Optional<AuthorEntity> result = underTest.findById(author.getId());
        assertEquals(result.get(), author);
    }

    @Test
    public void testDeleteAuthor() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        underTest.save(author);

        underTest.deleteById(author.getId());

        Optional<AuthorEntity> result = underTest.findById(author.getId());
        assertEquals(result.isEmpty(), true);
    }

    @Test
    public void testGetAuthorsWithAgeLessThan() {
        AuthorEntity authorB = TestDataUtil.createTestAuthorB();
        AuthorEntity authorE = TestDataUtil.createTestAuthorE();
        AuthorEntity authorF = TestDataUtil.createTestAuthorF();
        underTest.saveAll(List.of(authorB, authorE, authorF));

        Collection<AuthorEntity> results =
                IterableUtil.toCollection(underTest.findByAgeLessThan(75));
        assertEquals(results.size(), 2);
        assertEquals(results.contains(authorE), true);
        assertEquals(results.contains(authorF), true);
    }

}

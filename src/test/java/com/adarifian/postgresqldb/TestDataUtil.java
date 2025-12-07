package com.adarifian.postgresqldb;

import com.adarifian.postgresqldb.domain.entities.AuthorEntity;
import com.adarifian.postgresqldb.domain.entities.BookEntity;

public class TestDataUtil {
    private TestDataUtil() {}

    public static AuthorEntity createTestAuthorA() {
        return AuthorEntity.builder().name("James").age(50).build();
    }

    public static AuthorEntity createTestAuthorB() {
        return AuthorEntity.builder().name("Tolkien").age(80).build();
    }

    public static AuthorEntity createTestAuthorC() {
        return AuthorEntity.builder().name("Richard").age(40).build();
    }

    public static AuthorEntity createTestAuthorD() {
        return AuthorEntity.builder().name("Jeremy").age(50).build();
    }

    public static AuthorEntity createTestAuthorE() {
        return AuthorEntity.builder().name("GoT Writer").age(70).build();
    }

    public static AuthorEntity createTestAuthorF() {
        return AuthorEntity.builder().name("Sun Tzu").age(30).build();
    }

    public static BookEntity createTestBookA(AuthorEntity author) {
        return BookEntity.builder().isbn("123").title("LOTR").author(author).build();
    }

    public static BookEntity createTestBookB(AuthorEntity author) {
        return BookEntity.builder().isbn("1234").title("Game of Thrones").author(author).build();
    }

    public static BookEntity createTestBookC(AuthorEntity author) {
        return BookEntity.builder().isbn("12345").title("Art of War").author(author).build();
    }

}

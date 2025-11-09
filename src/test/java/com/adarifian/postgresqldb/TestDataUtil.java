package com.adarifian.postgresqldb;

import com.adarifian.postgresqldb.domain.Author;
import com.adarifian.postgresqldb.domain.Book;

public class TestDataUtil {
    private TestDataUtil() {
    }

    public static Author createTestAuthorA() {
        return Author.builder()
                .name("James")
                .age(50)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .name("Tolkien")
                .age(80)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .name("Richard")
                .age(40)
                .build();
    }

    public static Author createTestAuthorD() {
        return Author.builder()
                .name("Jeremy")
                .age(50)
                .build();
    }

    public static Author createTestAuthorE() {
        return Author.builder()
                .name("GoT Writer")
                .age(70)
                .build();
    }

    public static Author createTestAuthorF() {
        return Author.builder()
                .name("Sun Tzu")
                .age(30)
                .build();
    }

    public static Book createTestBookA(Author author) {
        return Book.builder()
                .isbn("123")
                .title("LOTR")
                .author(author)
                .build();
    }

    public static Book createTestBookB(Author author) {
        return Book.builder()
                .isbn("1234")
                .title("Game of Thrones")
                .author(author)
                .build();
    }

    public static Book createTestBookC(Author author) {
        return Book.builder()
                .isbn("12345")
                .title("Art of War")
                .author(author)
                .build();
    }

}

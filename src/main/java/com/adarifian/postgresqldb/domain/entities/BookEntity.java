package com.adarifian.postgresqldb.domain.entities;

import org.hibernate.annotations.Cascade;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookEntity {

    @Id
    private String isbn;

    private String title;

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @ManyToOne()
    @JoinColumn(name = "author_id")
    private AuthorEntity author;
}

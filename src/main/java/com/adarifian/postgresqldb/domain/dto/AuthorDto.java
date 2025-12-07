package com.adarifian.postgresqldb.domain.dto;

import com.adarifian.postgresqldb.domain.entities.AuthorEntity;

import lombok.With;

@With
public record AuthorDto(Long id, String name, int age) {
    public static AuthorDto from(AuthorEntity authorEntity) {
        if (authorEntity == null) {
            return null;
        }
        return new AuthorDto(authorEntity.getId(), authorEntity.getName(), authorEntity.getAge());
    }

    public AuthorEntity toEntity() {
        return AuthorEntity.builder().id(id()).name(name()).age(age()).build();
    }
}

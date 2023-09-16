package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity;

import java.util.Objects;

public final class PersonalRecordsEntity {
    private String email;
    private String document;
    private TypeEntity typeEntity;

    public PersonalRecordsEntity(
            final String email,
            final String document,
            final TypeEntity typeEntity
    ) {
        this.email = email;
        this.document = document;
        this.typeEntity = typeEntity;
    }

    public String getEmail() {
        return email;
    }

    public String getDocument() {
        return document;
    }

    public TypeEntity getTypeEntity() {
        return typeEntity;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PersonalRecordsEntity that = (PersonalRecordsEntity) object;
        return Objects.equals(email, that.email) && Objects.equals(document, that.document) && typeEntity == that.typeEntity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, document, typeEntity);
    }

}

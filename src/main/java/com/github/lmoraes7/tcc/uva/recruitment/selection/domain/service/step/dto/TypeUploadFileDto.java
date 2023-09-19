package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeFile;

import java.util.Objects;

public final class TypeUploadFileDto {
    private final String description;
    private final TypeFile type;

    public TypeUploadFileDto(final String description, final TypeFile type) {
        this.description = description;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public TypeFile getType() {
        return type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TypeUploadFileDto that = (TypeUploadFileDto) object;
        return Objects.equals(description, that.description) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, type);
    }

}

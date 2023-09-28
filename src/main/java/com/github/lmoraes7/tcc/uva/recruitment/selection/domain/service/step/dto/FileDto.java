package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeFile;

import java.util.Objects;

public final class FileDto {
    private final String description;
    private final TypeFile typeFile;

    public FileDto(final String description, final TypeFile typeFile) {
        this.description = description;
        this.typeFile = typeFile;
    }

    public String getDescription() {
        return description;
    }

    public TypeFile getTypeFile() {
        return typeFile;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        FileDto fileDto = (FileDto) object;
        return Objects.equals(description, fileDto.description) && typeFile == fileDto.typeFile;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, typeFile);
    }

}

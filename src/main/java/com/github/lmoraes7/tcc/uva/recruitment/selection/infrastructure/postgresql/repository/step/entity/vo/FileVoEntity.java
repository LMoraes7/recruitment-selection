package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity.vo;

import java.util.Arrays;
import java.util.Objects;

public final class FileVoEntity {
    private String description;
    private byte[] value;
    private String type;

    public FileVoEntity(
            final String description,
            final String type
    ) {
        this.description = description;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        FileVoEntity that = (FileVoEntity) object;
        return Objects.equals(description, that.description) && Arrays.equals(value, that.value) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(description, type);
        result = 31 * result + Arrays.hashCode(value);
        return result;
    }

}

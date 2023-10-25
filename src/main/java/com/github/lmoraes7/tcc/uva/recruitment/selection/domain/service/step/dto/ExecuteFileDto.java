package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeFile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;

import java.util.Arrays;
import java.util.Objects;

public final class ExecuteFileDto {
    private final String name;
    private final byte[] bytes;
    private final TypeFile type;

    public ExecuteFileDto(
            final String name,
            final byte[] bytes,
            final TypeFile type
    ) {
        this.name = name;
        this.bytes = bytes;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public TypeFile getType() {
        return type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ExecuteFileDto that = (ExecuteFileDto) object;
        return Objects.equals(name, that.name) && Arrays.equals(bytes, that.bytes) && type == that.type;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, type);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }

}

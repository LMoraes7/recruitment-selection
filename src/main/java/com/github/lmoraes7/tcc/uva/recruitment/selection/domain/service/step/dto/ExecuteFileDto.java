package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeFile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;

import java.util.Arrays;
import java.util.Objects;

public final class ExecuteFileDto {
    private final byte[] bytes;
    private final TypeFile type;

    public ExecuteFileDto(
            final byte[] bytes,
            final TypeFile type
    ) {
        this.bytes = bytes;
        this.type = type;
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
        return Arrays.equals(bytes, that.bytes) && type == that.type;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(type);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }

}

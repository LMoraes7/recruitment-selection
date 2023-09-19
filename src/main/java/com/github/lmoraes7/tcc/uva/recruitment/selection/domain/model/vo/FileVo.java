package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeFile;

import java.util.Arrays;
import java.util.Objects;

public final class FileVo {
    private String description;
    private byte[] value;
    private TypeFile type;

    public FileVo(final String description, final TypeFile type) {
        this.description = description;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getValue() {
        return value;
    }

    public TypeFile getType() {
        return type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        FileVo fileVo = (FileVo) object;
        return Objects.equals(description, fileVo.description) && Arrays.equals(value, fileVo.value) && type == fileVo.type;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(description, type);
        result = 31 * result + Arrays.hashCode(value);
        return result;
    }

}

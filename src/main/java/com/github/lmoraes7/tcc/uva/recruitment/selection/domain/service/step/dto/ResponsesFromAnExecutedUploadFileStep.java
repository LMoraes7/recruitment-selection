package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeFile;

import java.util.Arrays;
import java.util.Objects;

public final class ResponsesFromAnExecutedUploadFileStep {
    private final String name;
    private final byte[] bytes;
    private final TypeFile typeFile;

    public ResponsesFromAnExecutedUploadFileStep(final String name, final byte[] bytes, final TypeFile typeFile) {
        this.name = name;
        this.bytes = bytes;
        this.typeFile = typeFile;
    }

    public String getName() {
        return name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public TypeFile getTypeFile() {
        return typeFile;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ResponsesFromAnExecutedUploadFileStep that = (ResponsesFromAnExecutedUploadFileStep) object;
        return Objects.equals(name, that.name) && Arrays.equals(bytes, that.bytes) && typeFile == that.typeFile;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, typeFile);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }

}

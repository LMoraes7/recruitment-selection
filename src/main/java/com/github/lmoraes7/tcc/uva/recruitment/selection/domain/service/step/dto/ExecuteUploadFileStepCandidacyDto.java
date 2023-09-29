package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ExecuteUploadFileStepCandidacyDto {
    private final List<ExecuteFileDto> files;

    public ExecuteUploadFileStepCandidacyDto(final List<ExecuteFileDto> files) {
        this.files = files;
    }

    public List<ExecuteFileDto> getFiles() {
        return Collections.unmodifiableList(files);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ExecuteUploadFileStepCandidacyDto that = (ExecuteUploadFileStepCandidacyDto) object;
        return Objects.equals(files, that.files);
    }

    @Override
    public int hashCode() {
        return Objects.hash(files);
    }

}

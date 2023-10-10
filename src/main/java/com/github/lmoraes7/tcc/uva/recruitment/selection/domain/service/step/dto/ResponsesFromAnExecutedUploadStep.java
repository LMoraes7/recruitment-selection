package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ResponsesFromAnExecutedUploadStep {
    private final List<ResponsesFromAnExecutedUploadFileStep> files;

    public ResponsesFromAnExecutedUploadStep(final List<ResponsesFromAnExecutedUploadFileStep> files) {
        this.files = files;
    }

    public List<ResponsesFromAnExecutedUploadFileStep> getFiles() {
        return Collections.unmodifiableList(files);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ResponsesFromAnExecutedUploadStep that = (ResponsesFromAnExecutedUploadStep) object;
        return Objects.equals(files, that.files);
    }

    @Override
    public int hashCode() {
        return Objects.hash(files);
    }

}

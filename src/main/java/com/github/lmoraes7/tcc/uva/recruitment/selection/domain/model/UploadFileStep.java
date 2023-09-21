package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.FileVo;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public final class UploadFileStep implements StepSelectiveProcess {
    private StepData data;
    private Set<FileVo> files;
    private Long limitTime;

    public UploadFileStep(final StepData data, final Set<FileVo> files) {
        this.data = data;
        this.files = files;
    }

    @Override
    public StepData getData() {
        return data;
    }

    public Set<FileVo> getFiles() {
        return Collections.unmodifiableSet(files);
    }

    @Override
    public Long getLimitTime() {
        return limitTime;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UploadFileStep that = (UploadFileStep) object;
        return Objects.equals(data, that.data) && Objects.equals(files, that.files) && Objects.equals(limitTime, that.limitTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, files, limitTime);
    }

}

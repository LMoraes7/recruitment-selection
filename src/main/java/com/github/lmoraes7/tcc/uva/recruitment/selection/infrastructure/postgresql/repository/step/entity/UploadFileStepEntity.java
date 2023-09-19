package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity;

import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity.vo.FileVoEntity;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public final class UploadFileStepEntity extends StepEntity {
    private Set<FileVoEntity> files;

    public UploadFileStepEntity(
            final String identifier,
            final String title,
            final String description,
            final String type,
            final Set<FileVoEntity> files
    ) {
        super(identifier, title, description, type);
        this.files = files;
    }

    public Set<FileVoEntity> getFiles() {
        return Collections.unmodifiableSet(files);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        UploadFileStepEntity that = (UploadFileStepEntity) object;
        return Objects.equals(files, that.files);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), files);
    }

}

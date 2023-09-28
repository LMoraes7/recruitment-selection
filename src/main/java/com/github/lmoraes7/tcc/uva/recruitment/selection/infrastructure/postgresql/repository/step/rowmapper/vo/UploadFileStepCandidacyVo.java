package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeFile;

import java.util.Objects;

public final class UploadFileStepCandidacyVo {
    private final String candidacyIdentifier;
    private final String candidateIdentifier;
    private final String selectiveProcessIdentifier;
    private final String stepIdentifier;
    private final String fileDescription;
    private final TypeFile fileType;

    public UploadFileStepCandidacyVo(
            final String candidacyIdentifier,
            final String candidateIdentifier,
            final String selectiveProcessIdentifier,
            final String stepIdentifier,
            final String fileDescription,
            final TypeFile fileType
    ) {
        this.candidacyIdentifier = candidacyIdentifier;
        this.candidateIdentifier = candidateIdentifier;
        this.selectiveProcessIdentifier = selectiveProcessIdentifier;
        this.stepIdentifier = stepIdentifier;
        this.fileDescription = fileDescription;
        this.fileType = fileType;
    }

    public String getCandidacyIdentifier() {
        return candidacyIdentifier;
    }

    public String getCandidateIdentifier() {
        return candidateIdentifier;
    }

    public String getSelectiveProcessIdentifier() {
        return selectiveProcessIdentifier;
    }

    public String getStepIdentifier() {
        return stepIdentifier;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public TypeFile getFileType() {
        return fileType;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UploadFileStepCandidacyVo that = (UploadFileStepCandidacyVo) object;
        return Objects.equals(candidacyIdentifier, that.candidacyIdentifier) && Objects.equals(candidateIdentifier, that.candidateIdentifier) && Objects.equals(selectiveProcessIdentifier, that.selectiveProcessIdentifier) && Objects.equals(stepIdentifier, that.stepIdentifier) && Objects.equals(fileDescription, that.fileDescription) && fileType == that.fileType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidacyIdentifier, candidateIdentifier, selectiveProcessIdentifier, stepIdentifier, fileDescription, fileType);
    }

}

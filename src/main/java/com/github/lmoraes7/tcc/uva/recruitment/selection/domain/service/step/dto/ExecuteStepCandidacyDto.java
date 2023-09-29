package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;

public final class ExecuteStepCandidacyDto {
    private final String stepIdentifier;
    private final String candidacyIdentifier;
    private final String selectiveProcessIdentifier;
    private final TypeStep type;
    private final ExecuteTheoricalTestStepCandidacyDto theoricalTest;
    private final ExecuteUploadFileStepCandidacyDto uploadFile;

    public ExecuteStepCandidacyDto(
            final String stepIdentifier,
            final String candidacyIdentifier,
            final String selectiveProcessIdentifier,
            final TypeStep type,
            final ExecuteTheoricalTestStepCandidacyDto theoricalTest,
            final ExecuteUploadFileStepCandidacyDto uploadFile
    ) {
        this.stepIdentifier = stepIdentifier;
        this.candidacyIdentifier = candidacyIdentifier;
        this.selectiveProcessIdentifier = selectiveProcessIdentifier;
        this.type = type;
        this.theoricalTest = theoricalTest;
        this.uploadFile = uploadFile;
    }

    public String getStepIdentifier() {
        return stepIdentifier;
    }

    public String getCandidacyIdentifier() {
        return candidacyIdentifier;
    }

    public String getSelectiveProcessIdentifier() {
        return selectiveProcessIdentifier;
    }

    public TypeStep getType() {
        return type;
    }

    public ExecuteTheoricalTestStepCandidacyDto getTheoricalTest() {
        return theoricalTest;
    }

    public ExecuteUploadFileStepCandidacyDto getUploadFile() {
        return uploadFile;
    }

}

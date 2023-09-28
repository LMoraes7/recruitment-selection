package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import java.util.Objects;

public final class SpecificExecutionStepCandidacyDto {
    private final String candidacyIdentifier;
    private final String candidateIdentifier;
    private final String selectiveProcessIdentifier;
    private final String stepIdentifier;
    private final SpecificTheoricalTestStepCandidacyDto theoricalTestStep;
    private final SpecificUploadFileStepCandidacyDto uploadFileStep;
    private final SpecificExternalStepCandidacyDto externalStep;

    public SpecificExecutionStepCandidacyDto(
            final String candidacyIdentifier,
            final String candidateIdentifier,
            final String selectiveProcessIdentifier,
            final String stepIdentifier,
            final SpecificTheoricalTestStepCandidacyDto theoricalTestStep,
            final SpecificUploadFileStepCandidacyDto uploadFileStep,
            final SpecificExternalStepCandidacyDto externalStep
    ) {
        this.candidacyIdentifier = candidacyIdentifier;
        this.candidateIdentifier = candidateIdentifier;
        this.selectiveProcessIdentifier = selectiveProcessIdentifier;
        this.stepIdentifier = stepIdentifier;
        this.theoricalTestStep = theoricalTestStep;
        this.uploadFileStep = uploadFileStep;
        this.externalStep = externalStep;
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

    public SpecificTheoricalTestStepCandidacyDto getTheoricalTestStep() {
        return theoricalTestStep;
    }

    public SpecificUploadFileStepCandidacyDto getUploadFileStep() {
        return uploadFileStep;
    }

    public SpecificExternalStepCandidacyDto getExternalStep() {
        return externalStep;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SpecificExecutionStepCandidacyDto that = (SpecificExecutionStepCandidacyDto) object;
        return Objects.equals(candidacyIdentifier, that.candidacyIdentifier) && Objects.equals(candidateIdentifier, that.candidateIdentifier) && Objects.equals(selectiveProcessIdentifier, that.selectiveProcessIdentifier) && Objects.equals(stepIdentifier, that.stepIdentifier) && Objects.equals(theoricalTestStep, that.theoricalTestStep) && Objects.equals(uploadFileStep, that.uploadFileStep) && Objects.equals(externalStep, that.externalStep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidacyIdentifier, candidateIdentifier, selectiveProcessIdentifier, stepIdentifier, theoricalTestStep, uploadFileStep, externalStep);
    }

}

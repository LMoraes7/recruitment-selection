package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class SpecificExecutionStepCandidacyResponse {
    private String candidacyIdentifier;
    private String candidateIdentifier;
    private String selectiveProcessIdentifier;
    private String stepIdentifier;
    private SpecificTheoricalTestStepCandidacyResponse theoricalTestStep;
    private SpecificUploadFileStepCandidacyResponse uploadFileStep;
    private SpecificExternalStepCandidacyResponse externalStep;

    public SpecificExecutionStepCandidacyResponse(
            String candidacyIdentifier,
            String candidateIdentifier,
            String selectiveProcessIdentifier,
            String stepIdentifier,
            SpecificTheoricalTestStepCandidacyResponse theoricalTestStep,
            SpecificUploadFileStepCandidacyResponse uploadFileStep,
            SpecificExternalStepCandidacyResponse externalStep
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

    public void setCandidacyIdentifier(String candidacyIdentifier) {
        this.candidacyIdentifier = candidacyIdentifier;
    }

    public String getCandidateIdentifier() {
        return candidateIdentifier;
    }

    public void setCandidateIdentifier(String candidateIdentifier) {
        this.candidateIdentifier = candidateIdentifier;
    }

    public String getSelectiveProcessIdentifier() {
        return selectiveProcessIdentifier;
    }

    public void setSelectiveProcessIdentifier(String selectiveProcessIdentifier) {
        this.selectiveProcessIdentifier = selectiveProcessIdentifier;
    }

    public String getStepIdentifier() {
        return stepIdentifier;
    }

    public void setStepIdentifier(String stepIdentifier) {
        this.stepIdentifier = stepIdentifier;
    }

    public SpecificTheoricalTestStepCandidacyResponse getTheoricalTestStep() {
        return theoricalTestStep;
    }

    public void setTheoricalTestStep(SpecificTheoricalTestStepCandidacyResponse theoricalTestStep) {
        this.theoricalTestStep = theoricalTestStep;
    }

    public SpecificUploadFileStepCandidacyResponse getUploadFileStep() {
        return uploadFileStep;
    }

    public void setUploadFileStep(SpecificUploadFileStepCandidacyResponse uploadFileStep) {
        this.uploadFileStep = uploadFileStep;
    }

    public SpecificExternalStepCandidacyResponse getExternalStep() {
        return externalStep;
    }

    public void setExternalStep(SpecificExternalStepCandidacyResponse externalStep) {
        this.externalStep = externalStep;
    }

}

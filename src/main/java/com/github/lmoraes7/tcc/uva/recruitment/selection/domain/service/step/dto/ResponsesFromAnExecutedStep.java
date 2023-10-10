package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;

public final class ResponsesFromAnExecutedStep {
    private final String candidacyIdentifier;
    private final String stepIdentifier;
    private final TypeStep typeStep;
    private final ResponsesFromAnExecutedTheoricalStep theoricalStepExecuted;
    private final ResponsesFromAnExecutedUploadStep uploadStepExecuted;

    public ResponsesFromAnExecutedStep(
            final String candidacyIdentifier,
            final String stepIdentifier,
            final TypeStep typeStep,
            final ResponsesFromAnExecutedTheoricalStep theoricalStepExecuted,
            final ResponsesFromAnExecutedUploadStep uploadStepExecuted
    ) {
        this.candidacyIdentifier = candidacyIdentifier;
        this.stepIdentifier = stepIdentifier;
        this.typeStep = typeStep;
        this.theoricalStepExecuted = theoricalStepExecuted;
        this.uploadStepExecuted = uploadStepExecuted;
    }

    public String getCandidacyIdentifier() {
        return candidacyIdentifier;
    }

    public String getStepIdentifier() {
        return stepIdentifier;
    }

    public TypeStep getTypeStep() {
        return typeStep;
    }

    public ResponsesFromAnExecutedTheoricalStep getTheoricalStepExecuted() {
        return theoricalStepExecuted;
    }

    public ResponsesFromAnExecutedUploadStep getUploadStepExecuted() {
        return uploadStepExecuted;
    }

}

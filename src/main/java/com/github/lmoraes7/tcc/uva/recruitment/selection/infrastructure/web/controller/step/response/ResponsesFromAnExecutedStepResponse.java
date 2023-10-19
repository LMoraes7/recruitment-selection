package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class ResponsesFromAnExecutedStepResponse {
    private String candidacyIdentifier;
    private String stepIdentifier;
    private String typeStep;
    private ResponsesFromAnExecutedTheoricalStepResponse theoricalStepExecuted;
    private ResponsesFromAnExecutedUploadStepResponse uploadStepExecuted;

    public ResponsesFromAnExecutedStepResponse(
            final String candidacyIdentifier,
            final String stepIdentifier,
            final String typeStep,
            final ResponsesFromAnExecutedTheoricalStepResponse theoricalStepExecuted,
            final ResponsesFromAnExecutedUploadStepResponse uploadStepExecuted
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

    public void setCandidacyIdentifier(String candidacyIdentifier) {
        this.candidacyIdentifier = candidacyIdentifier;
    }

    public String getStepIdentifier() {
        return stepIdentifier;
    }

    public void setStepIdentifier(String stepIdentifier) {
        this.stepIdentifier = stepIdentifier;
    }

    public String getTypeStep() {
        return typeStep;
    }

    public void setTypeStep(String typeStep) {
        this.typeStep = typeStep;
    }

    public ResponsesFromAnExecutedTheoricalStepResponse getTheoricalStepExecuted() {
        return theoricalStepExecuted;
    }

    public void setTheoricalStepExecuted(ResponsesFromAnExecutedTheoricalStepResponse theoricalStepExecuted) {
        this.theoricalStepExecuted = theoricalStepExecuted;
    }

    public ResponsesFromAnExecutedUploadStepResponse getUploadStepExecuted() {
        return uploadStepExecuted;
    }

    public void setUploadStepExecuted(ResponsesFromAnExecutedUploadStepResponse uploadStepExecuted) {
        this.uploadStepExecuted = uploadStepExecuted;
    }

}

package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class ExecuteStepCandidacyTheoricalTestRequest {
    @NotNull
    @Size(min = 1)
    @Valid
    private List<ExecuteQuestionRequest> questions;

    public List<ExecuteQuestionRequest> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ExecuteQuestionRequest> questions) {
        this.questions = questions;
    }

}

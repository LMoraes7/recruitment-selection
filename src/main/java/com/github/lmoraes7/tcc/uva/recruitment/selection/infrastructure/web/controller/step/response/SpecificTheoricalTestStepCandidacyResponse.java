package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class SpecificTheoricalTestStepCandidacyResponse {
    private List<QuestionResponse> questions;

    public SpecificTheoricalTestStepCandidacyResponse(List<QuestionResponse> questions) {
        this.questions = questions;
    }

    public List<QuestionResponse> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionResponse> questions) {
        this.questions = questions;
    }

}

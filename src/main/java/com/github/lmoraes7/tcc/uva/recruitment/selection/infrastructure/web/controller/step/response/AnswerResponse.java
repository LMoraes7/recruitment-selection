package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class AnswerResponse {
    private String answerIdentifier;
    private String answerDescription;

    public AnswerResponse(String answerIdentifier, String answerDescription) {
        this.answerIdentifier = answerIdentifier;
        this.answerDescription = answerDescription;
    }

    public String getAnswerIdentifier() {
        return answerIdentifier;
    }

    public void setAnswerIdentifier(String answerIdentifier) {
        this.answerIdentifier = answerIdentifier;
    }

    public String getAnswerDescription() {
        return answerDescription;
    }

    public void setAnswerDescription(String answerDescription) {
        this.answerDescription = answerDescription;
    }

}

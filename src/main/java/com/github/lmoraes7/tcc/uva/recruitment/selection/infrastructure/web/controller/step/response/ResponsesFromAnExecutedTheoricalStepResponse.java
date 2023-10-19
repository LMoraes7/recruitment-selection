package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class ResponsesFromAnExecutedTheoricalStepResponse {
    private List<ResponsesFromAnExecutedTheoricalQuestionStepReponse> questionsExecuteds;

    public ResponsesFromAnExecutedTheoricalStepResponse(final List<ResponsesFromAnExecutedTheoricalQuestionStepReponse> questionsExecuteds) {
        this.questionsExecuteds = questionsExecuteds;
    }

    public List<ResponsesFromAnExecutedTheoricalQuestionStepReponse> getQuestionsExecuteds() {
        return questionsExecuteds;
    }

    public void setQuestionsExecuteds(List<ResponsesFromAnExecutedTheoricalQuestionStepReponse> questionsExecuteds) {
        this.questionsExecuteds = questionsExecuteds;
    }

}

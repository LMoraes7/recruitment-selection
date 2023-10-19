package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class QuestionResponse {
    private String questionIdentifier;
    private String questionDescription;
    private String questionType;
    private List<AnswerResponse> answers;

    public QuestionResponse(String questionIdentifier, String questionDescription, String questionType, List<AnswerResponse> answers) {
        this.questionIdentifier = questionIdentifier;
        this.questionDescription = questionDescription;
        this.questionType = questionType;
        this.answers = answers;
    }

    public String getQuestionIdentifier() {
        return questionIdentifier;
    }

    public void setQuestionIdentifier(String questionIdentifier) {
        this.questionIdentifier = questionIdentifier;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public List<AnswerResponse> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerResponse> answers) {
        this.answers = answers;
    }

}

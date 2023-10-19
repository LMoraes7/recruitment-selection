package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class ResponsesFromAnExecutedTheoricalQuestionStepReponse {
    private String questionIdentifier;
    private String answerIdentifier;
    private String typeQuestion;
    private String questionDescription;
    private String answerDescription;
    private String discursiveAnswer;
    private Boolean answerCorrect;

    public ResponsesFromAnExecutedTheoricalQuestionStepReponse(
            final String questionIdentifier,
            final String answerIdentifier,
            final String typeQuestion,
            final String questionDescription,
            final String answerDescription,
            final String discursiveAnswer,
            final Boolean answerCorrect
    ) {
        this.questionIdentifier = questionIdentifier;
        this.answerIdentifier = answerIdentifier;
        this.typeQuestion = typeQuestion;
        this.questionDescription = questionDescription;
        this.answerDescription = answerDescription;
        this.discursiveAnswer = discursiveAnswer;
        this.answerCorrect = answerCorrect;
    }

    public String getQuestionIdentifier() {
        return questionIdentifier;
    }

    public void setQuestionIdentifier(String questionIdentifier) {
        this.questionIdentifier = questionIdentifier;
    }

    public String getAnswerIdentifier() {
        return answerIdentifier;
    }

    public void setAnswerIdentifier(String answerIdentifier) {
        this.answerIdentifier = answerIdentifier;
    }

    public String getTypeQuestion() {
        return typeQuestion;
    }

    public void setTypeQuestion(String typeQuestion) {
        this.typeQuestion = typeQuestion;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public String getAnswerDescription() {
        return answerDescription;
    }

    public void setAnswerDescription(String answerDescription) {
        this.answerDescription = answerDescription;
    }

    public String getDiscursiveAnswer() {
        return discursiveAnswer;
    }

    public void setDiscursiveAnswer(String discursiveAnswer) {
        this.discursiveAnswer = discursiveAnswer;
    }

    public Boolean getAnswerCorrect() {
        return answerCorrect;
    }

    public void setAnswerCorrect(Boolean answerCorrect) {
        this.answerCorrect = answerCorrect;
    }

}

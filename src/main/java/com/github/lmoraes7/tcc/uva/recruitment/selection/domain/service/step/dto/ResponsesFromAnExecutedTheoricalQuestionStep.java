package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;

import java.util.Objects;

public final class ResponsesFromAnExecutedTheoricalQuestionStep {
    private final String questionIdentifier;
    private final String answerIdentifier;
    private final TypeQuestion typeQuestion;
    private final String questionDescription;
    private final String answerDescription;
    private final String discursiveAnswer;
    private final Boolean answerCorrect;

    public ResponsesFromAnExecutedTheoricalQuestionStep(
            final String questionIdentifier,
            final String answerIdentifier,
            final TypeQuestion typeQuestion,
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

    public String getAnswerIdentifier() {
        return answerIdentifier;
    }

    public TypeQuestion getTypeQuestion() {
        return typeQuestion;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public String getAnswerDescription() {
        return answerDescription;
    }

    public String getDiscursiveAnswer() {
        return discursiveAnswer;
    }

    public Boolean getAnswerCorrect() {
        return answerCorrect;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ResponsesFromAnExecutedTheoricalQuestionStep that = (ResponsesFromAnExecutedTheoricalQuestionStep) object;
        return Objects.equals(questionIdentifier, that.questionIdentifier) && Objects.equals(answerIdentifier, that.answerIdentifier) && typeQuestion == that.typeQuestion && Objects.equals(questionDescription, that.questionDescription) && Objects.equals(answerDescription, that.answerDescription) && Objects.equals(discursiveAnswer, that.discursiveAnswer) && Objects.equals(answerCorrect, that.answerCorrect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionIdentifier, answerIdentifier, typeQuestion, questionDescription, answerDescription, discursiveAnswer, answerCorrect);
    }

}

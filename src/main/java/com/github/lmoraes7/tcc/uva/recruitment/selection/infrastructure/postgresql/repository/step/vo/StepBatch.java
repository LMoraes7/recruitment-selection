package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.vo;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;

import java.util.Objects;

public final class StepBatch {
    private final String candidacyIdentifier;
    private final String stepIdentifier;
    private final String questionIdentifier;
    private final String answerIdentifier;
    private final TypeQuestion typeQuestion;
    private final String answerDiscursive;

    public StepBatch(
            final String candidacyIdentifier,
            final String stepIdentifier,
            final String questionIdentifier,
            final String answerIdentifier,
            final TypeQuestion typeQuestion,
            final String answerDiscursive
    ) {
        this.candidacyIdentifier = candidacyIdentifier;
        this.stepIdentifier = stepIdentifier;
        this.questionIdentifier = questionIdentifier;
        this.answerIdentifier = answerIdentifier;
        this.typeQuestion = typeQuestion;
        this.answerDiscursive = answerDiscursive;
    }

    public String getCandidacyIdentifier() {
        return candidacyIdentifier;
    }

    public String getStepIdentifier() {
        return stepIdentifier;
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

    public String getAnswerDiscursive() {
        return answerDiscursive;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        StepBatch stepBatch = (StepBatch) object;
        return Objects.equals(candidacyIdentifier, stepBatch.candidacyIdentifier) && Objects.equals(stepIdentifier, stepBatch.stepIdentifier) && Objects.equals(questionIdentifier, stepBatch.questionIdentifier) && Objects.equals(answerIdentifier, stepBatch.answerIdentifier) && typeQuestion == stepBatch.typeQuestion && Objects.equals(answerDiscursive, stepBatch.answerDiscursive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidacyIdentifier, stepIdentifier, questionIdentifier, answerIdentifier, typeQuestion, answerDiscursive);
    }

}

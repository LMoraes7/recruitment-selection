package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;

import java.util.Objects;

public final class TheoricalTestStepCandidacyVo {
    private final String candidacyIdentifier;
    private final String candidateIdentifier;
    private final String selectiveProcessIdentifier;
    private final String stepIdentifier;
    private final String questionIdentifier;
    private final String questionDescription;
    private final TypeQuestion questionType;
    private final String answerIdentifier;
    private final String answerDescription;

    public TheoricalTestStepCandidacyVo(
            final String candidacyIdentifier,
            final String candidateIdentifier,
            final String selectiveProcessIdentifier,
            final String stepIdentifier,
            final String questionIdentifier,
            final String questionDescription,
            final TypeQuestion questionType,
            final String answerIdentifier,
            final String answerDescription
    ) {
        this.candidacyIdentifier = candidacyIdentifier;
        this.candidateIdentifier = candidateIdentifier;
        this.selectiveProcessIdentifier = selectiveProcessIdentifier;
        this.stepIdentifier = stepIdentifier;
        this.questionIdentifier = questionIdentifier;
        this.questionDescription = questionDescription;
        this.questionType = questionType;
        this.answerIdentifier = answerIdentifier;
        this.answerDescription = answerDescription;
    }

    public String getCandidacyIdentifier() {
        return candidacyIdentifier;
    }

    public String getCandidateIdentifier() {
        return candidateIdentifier;
    }

    public String getSelectiveProcessIdentifier() {
        return selectiveProcessIdentifier;
    }

    public String getStepIdentifier() {
        return stepIdentifier;
    }

    public String getQuestionIdentifier() {
        return questionIdentifier;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public TypeQuestion getQuestionType() {
        return questionType;
    }

    public String getAnswerIdentifier() {
        return answerIdentifier;
    }

    public String getAnswerDescription() {
        return answerDescription;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TheoricalTestStepCandidacyVo that = (TheoricalTestStepCandidacyVo) object;
        return Objects.equals(candidacyIdentifier, that.candidacyIdentifier) && Objects.equals(candidateIdentifier, that.candidateIdentifier) && Objects.equals(selectiveProcessIdentifier, that.selectiveProcessIdentifier) && Objects.equals(stepIdentifier, that.stepIdentifier) && Objects.equals(questionIdentifier, that.questionIdentifier) && Objects.equals(questionDescription, that.questionDescription) && questionType == that.questionType && Objects.equals(answerIdentifier, that.answerIdentifier) && Objects.equals(answerDescription, that.answerDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidacyIdentifier, candidateIdentifier, selectiveProcessIdentifier, stepIdentifier, questionIdentifier, questionDescription, questionType, answerIdentifier, answerDescription);
    }

}

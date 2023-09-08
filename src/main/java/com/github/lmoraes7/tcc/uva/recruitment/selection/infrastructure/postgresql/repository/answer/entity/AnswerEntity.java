package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer.entity;

import java.util.Objects;

public final class AnswerEntity {
    private final String identifier;
    private final String description;
    private final Boolean isCorrect;
    private final String questionIdentifier;

    public AnswerEntity(
            final String identifier,
            final String description,
            final Boolean isCorrect,
            final String questionIdentifier
    ) {
        this.identifier = identifier;
        this.description = description;
        this.isCorrect = isCorrect;
        this.questionIdentifier = questionIdentifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public String getQuestionIdentifier() {
        return questionIdentifier;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AnswerEntity that = (AnswerEntity) object;
        return Objects.equals(identifier, that.identifier) && Objects.equals(description, that.description) && Objects.equals(isCorrect, that.isCorrect) && Objects.equals(questionIdentifier, that.questionIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, description, isCorrect, questionIdentifier);
    }

}

package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import java.util.Objects;

public final class Answer {
    private String identifier;
    private String description;
    private Boolean isCorrect;

    public String getIdentifier() {
        return identifier;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Answer answer = (Answer) object;
        return Objects.equals(identifier, answer.identifier) && Objects.equals(description, answer.description) && Objects.equals(isCorrect, answer.isCorrect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, description, isCorrect);
    }
}

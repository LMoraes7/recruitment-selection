package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public final class Question {
    private String identifier;
    private String description;
    private TypeQuestion type;
    private Set<Answer> answers;

    public Question(
            final String identifier,
            final String description,
            final TypeQuestion type
    ) {
        this.identifier = identifier;
        this.description = description;
        this.type = type;
    }

    public Question(
            final String identifier,
            final String description,
            final TypeQuestion type,
            final Set<Answer> answers
    ) {
        this.identifier = identifier;
        this.description = description;
        this.type = type;
        this.answers = answers;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getDescription() {
        return description;
    }

    public TypeQuestion getType() {
        return type;
    }

    public Set<Answer> getAnswers() {
        return Collections.unmodifiableSet(answers);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Question question = (Question) object;
        return Objects.equals(identifier, question.identifier) && Objects.equals(description, question.description) && type == question.type && Objects.equals(answers, question.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, description, type, answers);
    }

}

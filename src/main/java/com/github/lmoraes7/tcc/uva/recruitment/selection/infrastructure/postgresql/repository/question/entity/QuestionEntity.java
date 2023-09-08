package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.entity;

public final class QuestionEntity {
    private final String identifier;
    private final String description;
    private final String type;

    public QuestionEntity(
            final String identifier,
            final String description,
            final String type
    ) {
        this.identifier = identifier;
        this.description = description;
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

}

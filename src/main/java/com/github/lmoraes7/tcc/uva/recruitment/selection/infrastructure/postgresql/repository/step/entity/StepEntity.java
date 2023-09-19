package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity;

import java.util.Objects;

public abstract class StepEntity {
    private final String identifier;
    private final String title;
    private final String description;
    private final String type;

    public StepEntity(
            final String identifier,
            final String title,
            final String description,
            final String type
    ) {
        this.identifier = identifier;
        this.title = title;
        this.description = description;
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        StepEntity that = (StepEntity) object;
        return Objects.equals(identifier, that.identifier) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, title, description, type);
    }

}

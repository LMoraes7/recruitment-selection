package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;

import java.util.Objects;

public final class StepData {
    private String identifier;
    private String title;
    private String description;
    private TypeStep type;

    public StepData(final String identifier) {
        this.identifier = identifier;
    }

    public StepData(final String identifier, final TypeStep type) {
        this.identifier = identifier;
        this.type = type;
    }

    public StepData(
            final String identifier,
            final String title,
            final String description,
            final TypeStep type
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

    public TypeStep getType() {
        return type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        StepData stepData = (StepData) object;
        return Objects.equals(identifier, stepData.identifier) && Objects.equals(title, stepData.title) && Objects.equals(description, stepData.description) && type == stepData.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, title, description, type);
    }

}

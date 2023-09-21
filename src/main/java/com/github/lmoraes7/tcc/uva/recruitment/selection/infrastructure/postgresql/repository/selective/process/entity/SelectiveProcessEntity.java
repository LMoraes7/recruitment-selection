package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.entity;

import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.utils.ArraySqlValue;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class SelectiveProcessEntity {
    private final String identifier;
    private final String title;
    private final String description;
    private final String desiredPosition;
    private final String status;
    private final ArraySqlValue<String> responsibilities;
    private final ArraySqlValue<String> requirements;
    private final ArraySqlValue<String> additionalInfos;
    private final List<StepSelectiveProcessEntity> steps;

    public SelectiveProcessEntity(
            final String identifier,
            final String title,
            final String description,
            final String desiredPosition,
            final String status,
            final ArraySqlValue<String> responsibilities,
            final ArraySqlValue<String> requirements,
            final ArraySqlValue<String> additionalInfos,
            final List<StepSelectiveProcessEntity> steps
    ) {
        this.identifier = identifier;
        this.title = title;
        this.description = description;
        this.desiredPosition = desiredPosition;
        this.status = status;
        this.responsibilities = responsibilities;
        this.requirements = requirements;
        this.additionalInfos = additionalInfos;
        this.steps = steps;
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

    public String getDesiredPosition() {
        return desiredPosition;
    }

    public String getStatus() {
        return status;
    }

    public ArraySqlValue<String> getResponsibilities() {
        return responsibilities;
    }

    public ArraySqlValue<String> getRequirements() {
        return requirements;
    }

    public ArraySqlValue<String> getAdditionalInfos() {
        return additionalInfos;
    }

    public List<StepSelectiveProcessEntity> getSteps() {
        return Collections.unmodifiableList(steps);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SelectiveProcessEntity that = (SelectiveProcessEntity) object;
        return Objects.equals(identifier, that.identifier) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(desiredPosition, that.desiredPosition) && Objects.equals(status, that.status) && Objects.equals(responsibilities, that.responsibilities) && Objects.equals(requirements, that.requirements) && Objects.equals(additionalInfos, that.additionalInfos) && Objects.equals(steps, that.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, title, description, desiredPosition, status, responsibilities, requirements, additionalInfos, steps);
    }

}

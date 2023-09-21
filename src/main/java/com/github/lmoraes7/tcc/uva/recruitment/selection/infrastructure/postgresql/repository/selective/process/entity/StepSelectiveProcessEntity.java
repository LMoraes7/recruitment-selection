package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.entity;

import java.util.Objects;

public final class StepSelectiveProcessEntity {
    private final String identifier;
    private final Long limitTime;
    private final String nextStepIdentifier;

    public StepSelectiveProcessEntity(
            final String identifier,
            final Long limitTime,
            final String nextStepIdentifier
    ) {
        this.identifier = identifier;
        this.limitTime = limitTime;
        this.nextStepIdentifier = nextStepIdentifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Long getLimitTime() {
        return limitTime;
    }

    public String getNextStepIdentifier() {
        return nextStepIdentifier;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        StepSelectiveProcessEntity that = (StepSelectiveProcessEntity) object;
        return Objects.equals(identifier, that.identifier) && Objects.equals(limitTime, that.limitTime) && Objects.equals(nextStepIdentifier, that.nextStepIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, limitTime, nextStepIdentifier);
    }

}

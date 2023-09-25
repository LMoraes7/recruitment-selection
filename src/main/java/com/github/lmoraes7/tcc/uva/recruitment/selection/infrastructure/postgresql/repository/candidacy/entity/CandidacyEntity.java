package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.entity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class CandidacyEntity {
    private final String identifier;
    private final String status;
    private final String candidateIdentifier;
    private final String selectiveProcessIdentifier;
    private final List<StepCandidacyEntity> steps;

    public CandidacyEntity(
            final String identifier,
            final String status,
            final String candidateIdentifier,
            final String selectiveProcessIdentifier,
            final List<StepCandidacyEntity> steps
    ) {
        this.identifier = identifier;
        this.status = status;
        this.candidateIdentifier = candidateIdentifier;
        this.selectiveProcessIdentifier = selectiveProcessIdentifier;
        this.steps = steps;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getStatus() {
        return status;
    }

    public String getCandidateIdentifier() {
        return candidateIdentifier;
    }

    public String getSelectiveProcessIdentifier() {
        return selectiveProcessIdentifier;
    }

    public List<StepCandidacyEntity> getSteps() {
        return Collections.unmodifiableList(steps);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CandidacyEntity that = (CandidacyEntity) object;
        return Objects.equals(identifier, that.identifier) && Objects.equals(status, that.status) && Objects.equals(candidateIdentifier, that.candidateIdentifier) && Objects.equals(selectiveProcessIdentifier, that.selectiveProcessIdentifier) && Objects.equals(steps, that.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, status, candidateIdentifier, selectiveProcessIdentifier, steps);
    }

}

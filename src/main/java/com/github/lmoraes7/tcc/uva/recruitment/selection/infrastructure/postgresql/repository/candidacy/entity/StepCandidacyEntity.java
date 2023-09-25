package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.entity;

import java.time.LocalDate;
import java.util.Objects;

public final class StepCandidacyEntity {
    private final String identifier;
    private final Long limitTime;
    private final String nextStepIdentifier;
    private final String status;
    private final LocalDate releaseDate;

    public StepCandidacyEntity(
            final String identifier,
            final Long limitTime,
            final String nextStepIdentifier,
            final String status,
            final LocalDate releaseDate
    ) {
        this.identifier = identifier;
        this.limitTime = limitTime;
        this.nextStepIdentifier = nextStepIdentifier;
        this.status = status;
        this.releaseDate = releaseDate;
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

    public String getStatus() {
        return status;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        StepCandidacyEntity that = (StepCandidacyEntity) object;
        return Objects.equals(identifier, that.identifier) && Objects.equals(limitTime, that.limitTime) && Objects.equals(nextStepIdentifier, that.nextStepIdentifier) && Objects.equals(status, that.status) && Objects.equals(releaseDate, that.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, limitTime, nextStepIdentifier, status, releaseDate);
    }

}

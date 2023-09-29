package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;

import java.time.LocalDate;
import java.util.Objects;

public final class ExecuteStepCandidacyFindDto {
    private final String identifier;
    private final StatusStepCandidacy status;
    private final LocalDate limitTime;
    private final LocalDate releaseDate;

    public ExecuteStepCandidacyFindDto(
            final String identifier,
            final StatusStepCandidacy status,
            final LocalDate limitTime,
            final LocalDate releaseDate
    ) {
        this.identifier = identifier;
        this.status = status;
        this.limitTime = limitTime;
        this.releaseDate = releaseDate;
    }

    public String getIdentifier() {
        return identifier;
    }

    public StatusStepCandidacy getStatus() {
        return status;
    }

    public LocalDate getLimitTime() {
        return limitTime;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public boolean hasItPassedTheDeadline() {
        if (this.limitTime == null)
            return false;

        if (this.limitTime.isBefore(LocalDate.now()))
            return true;

        return false;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ExecuteStepCandidacyFindDto that = (ExecuteStepCandidacyFindDto) object;
        return Objects.equals(identifier, that.identifier) && status == that.status && Objects.equals(limitTime, that.limitTime) && Objects.equals(releaseDate, that.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, status, limitTime, releaseDate);
    }

}

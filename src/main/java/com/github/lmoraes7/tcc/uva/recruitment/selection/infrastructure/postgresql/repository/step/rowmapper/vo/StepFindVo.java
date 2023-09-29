package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;

import java.time.LocalDate;
import java.util.Objects;

public final class StepFindVo {
    private final String identifier;
    private final StatusStepCandidacy status;
    private final Long limitTime;
    private final LocalDate releaseDate;

    public StepFindVo(
            final String identifier,
            final StatusStepCandidacy status,
            final Long limitTime,
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

    public Long getLimitTime() {
        return limitTime;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        StepFindVo that = (StepFindVo) object;
        return Objects.equals(identifier, that.identifier) && status == that.status && Objects.equals(limitTime, that.limitTime) && Objects.equals(releaseDate, that.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, status, limitTime, releaseDate);
    }

}

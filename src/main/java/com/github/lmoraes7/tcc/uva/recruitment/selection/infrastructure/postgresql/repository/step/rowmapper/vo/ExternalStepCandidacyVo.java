package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo;

import java.time.LocalDateTime;
import java.util.Objects;

public final class ExternalStepCandidacyVo {
    private final String candidacyIdentifier;
    private final String candidateIdentifier;
    private final String selectiveProcessIdentifier;
    private final String stepIdentifier;
    private final String externalLink;
    private final LocalDateTime externalDateTime;

    public ExternalStepCandidacyVo(
            final String candidacyIdentifier,
            final String candidateIdentifier,
            final String selectiveProcessIdentifier,
            final String stepIdentifier,
            final String externalLink,
            final LocalDateTime externalDateTime
    ) {
        this.candidacyIdentifier = candidacyIdentifier;
        this.candidateIdentifier = candidateIdentifier;
        this.selectiveProcessIdentifier = selectiveProcessIdentifier;
        this.stepIdentifier = stepIdentifier;
        this.externalLink = externalLink;
        this.externalDateTime = externalDateTime;
    }

    public String getCandidacyIdentifier() {
        return candidacyIdentifier;
    }

    public String getCandidateIdentifier() {
        return candidateIdentifier;
    }

    public String getSelectiveProcessIdentifier() {
        return selectiveProcessIdentifier;
    }

    public String getStepIdentifier() {
        return stepIdentifier;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public LocalDateTime getExternalDateTime() {
        return externalDateTime;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ExternalStepCandidacyVo that = (ExternalStepCandidacyVo) object;
        return Objects.equals(candidacyIdentifier, that.candidacyIdentifier) && Objects.equals(candidateIdentifier, that.candidateIdentifier) && Objects.equals(selectiveProcessIdentifier, that.selectiveProcessIdentifier) && Objects.equals(stepIdentifier, that.stepIdentifier) && Objects.equals(externalLink, that.externalLink) && Objects.equals(externalDateTime, that.externalDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidacyIdentifier, candidateIdentifier, selectiveProcessIdentifier, stepIdentifier, externalLink, externalDateTime);
    }

}

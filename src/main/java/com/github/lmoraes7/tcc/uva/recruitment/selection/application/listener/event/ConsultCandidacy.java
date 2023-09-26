package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event;

import java.util.Objects;

public final class ConsultCandidacy {
    private final String candidacyIdentifier;
    private final String candidacyStatus;
    private final String selectiveProcessIdentifier;
    private final String candidateIdentifier;

    public ConsultCandidacy(
            final String candidacyIdentifier,
            final String candidacyStatus,
            final String selectiveProcessIdentifier,
            final String candidateIdentifier
    ) {
        this.candidacyIdentifier = candidacyIdentifier;
        this.candidacyStatus = candidacyStatus;
        this.selectiveProcessIdentifier = selectiveProcessIdentifier;
        this.candidateIdentifier = candidateIdentifier;
    }

    public String getCandidacyIdentifier() {
        return candidacyIdentifier;
    }

    public String getCandidacyStatus() {
        return candidacyStatus;
    }

    public String getSelectiveProcessIdentifier() {
        return selectiveProcessIdentifier;
    }

    public String getCandidateIdentifier() {
        return candidateIdentifier;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ConsultCandidacy that = (ConsultCandidacy) object;
        return Objects.equals(candidacyIdentifier, that.candidacyIdentifier) && Objects.equals(candidacyStatus, that.candidacyStatus) && Objects.equals(selectiveProcessIdentifier, that.selectiveProcessIdentifier) && Objects.equals(candidateIdentifier, that.candidateIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidacyIdentifier, candidacyStatus, selectiveProcessIdentifier, candidateIdentifier);
    }

}

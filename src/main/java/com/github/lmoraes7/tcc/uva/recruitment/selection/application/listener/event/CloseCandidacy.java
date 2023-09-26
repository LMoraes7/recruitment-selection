package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event;

import java.util.Objects;

public final class CloseCandidacy {
    private final String candidacyIdentifier;
    private final String candidateIdentifier;
    private final String selectiveProcessIdentifier;

    public CloseCandidacy(
            final String candidacyIdentifier,
            final String candidateIdentifier,
            final String selectiveProcessIdentifier
    ) {
        this.candidacyIdentifier = candidacyIdentifier;
        this.candidateIdentifier = candidateIdentifier;
        this.selectiveProcessIdentifier = selectiveProcessIdentifier;
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CloseCandidacy that = (CloseCandidacy) object;
        return Objects.equals(candidacyIdentifier, that.candidacyIdentifier) && Objects.equals(candidateIdentifier, that.candidateIdentifier) && Objects.equals(selectiveProcessIdentifier, that.selectiveProcessIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidacyIdentifier, candidateIdentifier, selectiveProcessIdentifier);
    }

}

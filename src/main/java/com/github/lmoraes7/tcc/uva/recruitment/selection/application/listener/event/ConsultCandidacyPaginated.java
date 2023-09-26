package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event;

import java.util.Objects;

public class ConsultCandidacyPaginated {
    private final String candidateIdentifier;

    public ConsultCandidacyPaginated(final String candidateIdentifier) {
        this.candidateIdentifier = candidateIdentifier;
    }

    public String getCandidateIdentifier() {
        return candidateIdentifier;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ConsultCandidacyPaginated that = (ConsultCandidacyPaginated) object;
        return Objects.equals(candidateIdentifier, that.candidateIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidateIdentifier);
    }

}

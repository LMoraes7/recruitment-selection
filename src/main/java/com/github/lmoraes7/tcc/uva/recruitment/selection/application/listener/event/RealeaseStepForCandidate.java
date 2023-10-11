package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event;

import java.util.Objects;

public final class RealeaseStepForCandidate {
    private final String candidacyIdentifier;
    private final String stepIdentifier;

    public RealeaseStepForCandidate(
            final String candidacyIdentifier,
            final String stepIdentifier
    ) {
        this.candidacyIdentifier = candidacyIdentifier;
        this.stepIdentifier = stepIdentifier;
    }

    public String getCandidacyIdentifier() {
        return candidacyIdentifier;
    }

    public String getStepIdentifier() {
        return stepIdentifier;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        RealeaseStepForCandidate that = (RealeaseStepForCandidate) object;
        return Objects.equals(candidacyIdentifier, that.candidacyIdentifier) && Objects.equals(stepIdentifier, that.stepIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidacyIdentifier, stepIdentifier);
    }

}

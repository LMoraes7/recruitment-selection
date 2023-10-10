package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event;

import java.util.Objects;

public class ConsultExecutedStepCandidacy {
    private final String stepIdentifier;
    private final String candidacyIdentifier;
    private final String typeStep;

    public ConsultExecutedStepCandidacy(
            final String stepIdentifier,
            final String candidacyIdentifier,
            final String typeStep
    ) {
        this.stepIdentifier = stepIdentifier;
        this.candidacyIdentifier = candidacyIdentifier;
        this.typeStep = typeStep;
    }

    public String getStepIdentifier() {
        return stepIdentifier;
    }

    public String getCandidacyIdentifier() {
        return candidacyIdentifier;
    }

    public String getTypeStep() {
        return typeStep;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ConsultExecutedStepCandidacy that = (ConsultExecutedStepCandidacy) object;
        return Objects.equals(stepIdentifier, that.stepIdentifier) && Objects.equals(candidacyIdentifier, that.candidacyIdentifier) && Objects.equals(typeStep, that.typeStep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stepIdentifier, candidacyIdentifier, typeStep);
    }

}

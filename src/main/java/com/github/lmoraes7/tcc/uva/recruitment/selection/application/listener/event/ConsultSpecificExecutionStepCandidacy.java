package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event;

import java.util.Objects;

public class ConsultSpecificExecutionStepCandidacy  {
    private final String stepIdentifier;
    private final String candidacyIdentifier;
    private final String candidateIdentifier;
    private final String selectiveProcessIdentifier;
    private final String typeStep;

    public ConsultSpecificExecutionStepCandidacy(
            final String stepIdentifier,
            final String candidacyIdentifier,
            final String candidateIdentifier,
            final String selectiveProcessIdentifier,
            final String typeStep
    ) {
        this.stepIdentifier = stepIdentifier;
        this.candidacyIdentifier = candidacyIdentifier;
        this.candidateIdentifier = candidateIdentifier;
        this.selectiveProcessIdentifier = selectiveProcessIdentifier;
        this.typeStep = typeStep;
    }

    public String getStepIdentifier() {
        return stepIdentifier;
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

    public String getTypeStep() {
        return typeStep;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ConsultSpecificExecutionStepCandidacy that = (ConsultSpecificExecutionStepCandidacy) object;
        return Objects.equals(stepIdentifier, that.stepIdentifier) && Objects.equals(candidacyIdentifier, that.candidacyIdentifier) && Objects.equals(candidateIdentifier, that.candidateIdentifier) && Objects.equals(selectiveProcessIdentifier, that.selectiveProcessIdentifier) && Objects.equals(typeStep, that.typeStep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stepIdentifier, candidacyIdentifier, candidateIdentifier, selectiveProcessIdentifier, typeStep);
    }

}

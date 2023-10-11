package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;

import java.util.Objects;

public final class FindStepsDto {
    private final String stepIdentifier;
    private final String nextStepIdentifier;
    private final StatusStepCandidacy statusStepCandidacy;
    private final TypeStep typeStep;

    public FindStepsDto(
            final String stepIdentifier,
            final String nextStepIdentifier,
            final StatusStepCandidacy statusStepCandidacy,
            final TypeStep typeStep
            ) {
        this.stepIdentifier = stepIdentifier;
        this.nextStepIdentifier = nextStepIdentifier;
        this.statusStepCandidacy = statusStepCandidacy;
        this.typeStep = typeStep;
    }

    public String getStepIdentifier() {
        return stepIdentifier;
    }

    public String getNextStepIdentifier() {
        return nextStepIdentifier;
    }

    public StatusStepCandidacy getStatusStepCandidacy() {
        return statusStepCandidacy;
    }

    public TypeStep getTypeStep() {
        return typeStep;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        FindStepsDto that = (FindStepsDto) object;
        return Objects.equals(stepIdentifier, that.stepIdentifier) && Objects.equals(nextStepIdentifier, that.nextStepIdentifier) && statusStepCandidacy == that.statusStepCandidacy && typeStep == that.typeStep;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stepIdentifier, nextStepIdentifier, statusStepCandidacy, typeStep);
    }

}

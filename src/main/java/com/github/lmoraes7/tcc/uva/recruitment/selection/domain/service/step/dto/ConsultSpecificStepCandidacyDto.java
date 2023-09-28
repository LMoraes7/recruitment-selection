package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;

import java.util.Objects;

public final class ConsultSpecificStepCandidacyDto {
    private final String stepIdentifier;
    private final String candidacyIdentifier;
    private final String selectiveProcessIdentifier;
    private final TypeStep type;

    public ConsultSpecificStepCandidacyDto(
            final String stepIdentifier,
            final String candidacyIdentifier,
            final String selectiveProcessIdentifier,
            final TypeStep type
    ) {
        this.stepIdentifier = stepIdentifier;
        this.candidacyIdentifier = candidacyIdentifier;
        this.selectiveProcessIdentifier = selectiveProcessIdentifier;
        this.type = type;
    }

    public String getStepIdentifier() {
        return stepIdentifier;
    }

    public String getCandidacyIdentifier() {
        return candidacyIdentifier;
    }

    public String getSelectiveProcessIdentifier() {
        return selectiveProcessIdentifier;
    }

    public TypeStep getType() {
        return type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ConsultSpecificStepCandidacyDto that = (ConsultSpecificStepCandidacyDto) object;
        return Objects.equals(stepIdentifier, that.stepIdentifier) && Objects.equals(candidacyIdentifier, that.candidacyIdentifier) && Objects.equals(selectiveProcessIdentifier, that.selectiveProcessIdentifier) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stepIdentifier, candidacyIdentifier, selectiveProcessIdentifier, type);
    }

}

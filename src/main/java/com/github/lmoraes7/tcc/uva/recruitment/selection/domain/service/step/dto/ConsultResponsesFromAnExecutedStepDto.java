package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;

import java.util.Objects;

public final class ConsultResponsesFromAnExecutedStepDto {
    private final String stepIdentifier;
    private final String applicationIdentifier;
    private final TypeStep typeStep;

    public ConsultResponsesFromAnExecutedStepDto(
            final String stepIdentifier,
            final String applicationIdentifier,
            final TypeStep typeStep
    ) {
        this.stepIdentifier = stepIdentifier;
        this.applicationIdentifier = applicationIdentifier;
        this.typeStep = typeStep;
    }

    public String getStepIdentifier() {
        return stepIdentifier;
    }

    public String getApplicationIdentifier() {
        return applicationIdentifier;
    }

    public TypeStep getTypeStep() {
        return typeStep;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ConsultResponsesFromAnExecutedStepDto that = (ConsultResponsesFromAnExecutedStepDto) object;
        return Objects.equals(stepIdentifier, that.stepIdentifier) && Objects.equals(applicationIdentifier, that.applicationIdentifier) && typeStep == that.typeStep;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stepIdentifier, applicationIdentifier, typeStep);
    }

}

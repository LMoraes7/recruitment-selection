package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.feedback.dto;

import java.math.BigDecimal;
import java.util.Objects;

public final class RegisterFeedbackDto {
    private final String candidacyIdentifier;
    private final String stepIdentifier;
    private final String additionalInfo;
    private final BigDecimal pointing;

    public RegisterFeedbackDto(
            final String candidacyIdentifier,
            final String stepIdentifier,
            final String additionalInfo,
            final BigDecimal pointing
    ) {
        this.candidacyIdentifier = candidacyIdentifier;
        this.stepIdentifier = stepIdentifier;
        this.additionalInfo = additionalInfo;
        this.pointing = pointing;
    }

    public String getCandidacyIdentifier() {
        return candidacyIdentifier;
    }

    public String getStepIdentifier() {
        return stepIdentifier;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public BigDecimal getPointing() {
        return pointing;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        RegisterFeedbackDto that = (RegisterFeedbackDto) object;
        return Objects.equals(candidacyIdentifier, that.candidacyIdentifier) && Objects.equals(stepIdentifier, that.stepIdentifier) && Objects.equals(additionalInfo, that.additionalInfo) && Objects.equals(pointing, that.pointing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidacyIdentifier, stepIdentifier, additionalInfo, pointing);
    }

}

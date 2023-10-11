package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public final class ReleaseStepForCandidateDto {
    private final String stepIdentifier;
    private final String candidacyIdentifier;
    private final String link;
    private final LocalDateTime dateTime;

    public ReleaseStepForCandidateDto(
            final String stepIdentifier,
            final String candidacyIdentifier,
            final String link,
            final LocalDateTime dateTime
    ) {
        this.stepIdentifier = stepIdentifier;
        this.candidacyIdentifier = candidacyIdentifier;
        this.link = link;
        this.dateTime = dateTime;
    }

    public String getStepIdentifier() {
        return stepIdentifier;
    }

    public String getCandidacyIdentifier() {
        return candidacyIdentifier;
    }

    public String getLink() {
        return link;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ReleaseStepForCandidateDto that = (ReleaseStepForCandidateDto) object;
        return Objects.equals(stepIdentifier, that.stepIdentifier) && Objects.equals(candidacyIdentifier, that.candidacyIdentifier) && Objects.equals(link, that.link) && Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stepIdentifier, candidacyIdentifier, link, dateTime);
    }

}

package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public final class SpecificExternalStepCandidacyDto {
    private final String link;
    private final LocalDateTime dateTime;

    public SpecificExternalStepCandidacyDto(final String link, final LocalDateTime dateTime) {
        this.link = link;
        this.dateTime = dateTime;
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
        SpecificExternalStepCandidacyDto that = (SpecificExternalStepCandidacyDto) object;
        return Objects.equals(link, that.link) && Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, dateTime);
    }

}

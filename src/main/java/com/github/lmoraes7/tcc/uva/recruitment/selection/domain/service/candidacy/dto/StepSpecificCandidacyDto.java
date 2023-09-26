package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;

import java.util.Objects;

public final class StepSpecificCandidacyDto {
    private final String id;
    private final StatusStepCandidacy status;
    private final String title;
    private final TypeStep type;

    public StepSpecificCandidacyDto(
            final String id,
            final StatusStepCandidacy status,
            final String title,
            final TypeStep type
    ) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public StatusStepCandidacy getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public TypeStep getType() {
        return type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        StepSpecificCandidacyDto that = (StepSpecificCandidacyDto) object;
        return Objects.equals(id, that.id) && status == that.status && Objects.equals(title, that.title) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, title, type);
    }

}

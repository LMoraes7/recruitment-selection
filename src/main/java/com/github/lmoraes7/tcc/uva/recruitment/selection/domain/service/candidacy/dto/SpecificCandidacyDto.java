package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusCandidacy;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class SpecificCandidacyDto {
    private final String id;
    private final StatusCandidacy status;
    private final SelectiveProcessSpecificCandidacyDto selectiveProcess;
    private final List<StepSpecificCandidacyDto> steps;

    public SpecificCandidacyDto(
            final String id,
            final StatusCandidacy status,
            final SelectiveProcessSpecificCandidacyDto selectiveProcess,
            final List<StepSpecificCandidacyDto> steps
    ) {
        this.id = id;
        this.status = status;
        this.selectiveProcess = selectiveProcess;
        this.steps = steps;
    }

    public String getId() {
        return id;
    }

    public StatusCandidacy getStatus() {
        return status;
    }

    public SelectiveProcessSpecificCandidacyDto getSelectiveProcess() {
        return selectiveProcess;
    }

    public List<StepSpecificCandidacyDto> getSteps() {
        return Collections.unmodifiableList(steps);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SpecificCandidacyDto that = (SpecificCandidacyDto) object;
        return Objects.equals(id, that.id) && status == that.status && Objects.equals(selectiveProcess, that.selectiveProcess) && Objects.equals(steps, that.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, selectiveProcess, steps);
    }

}

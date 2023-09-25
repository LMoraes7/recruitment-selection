package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusCandidacy;

import java.util.Collections;
import java.util.List;

public final class Candidacy {
    private String identifier;
    private StatusCandidacy status;
    private List<StepCandidacy> steps;

    public Candidacy(final String identifier, final StatusCandidacy status, final List<StepCandidacy> steps) {
        this.identifier = identifier;
        this.status = status;
        this.steps = steps;
    }

    public String getIdentifier() {
        return identifier;
    }

    public StatusCandidacy getStatus() {
        return status;
    }

    public List<StepCandidacy> getSteps() {
        return Collections.unmodifiableList(steps);
    }

}

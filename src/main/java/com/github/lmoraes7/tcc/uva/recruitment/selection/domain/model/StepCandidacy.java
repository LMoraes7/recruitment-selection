package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;

import java.time.LocalDate;

public interface StepCandidacy extends Step {
    Long getLimitTime();

    StatusStepCandidacy getStatusStepCandidacy();

    LocalDate getReleaseData();
}

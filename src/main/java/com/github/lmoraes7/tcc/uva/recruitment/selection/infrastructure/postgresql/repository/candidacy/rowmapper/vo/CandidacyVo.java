package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.rowmapper.vo;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;

import java.util.Objects;

public final class CandidacyVo {
    private final String candidacyId;
    private final StatusCandidacy candidacyStatus;
    private final String selectiveProcessId;
    private final String selectiveProcessTitle;
    private final String stepId;
    private final String nextStepId;
    private final StatusStepCandidacy stepStatus;
    private final String stepTitle;
    private final TypeStep stepType;

    public CandidacyVo(
            final String candidacyId,
            final StatusCandidacy candidacyStatus,
            final String selectiveProcessId,
            final String selectiveProcessTitle,
            final String stepId,
            final String nextStepId,
            final StatusStepCandidacy stepStatus,
            final String stepTitle,
            final TypeStep stepType
    ) {
        this.candidacyId = candidacyId;
        this.candidacyStatus = candidacyStatus;
        this.selectiveProcessId = selectiveProcessId;
        this.selectiveProcessTitle = selectiveProcessTitle;
        this.stepId = stepId;
        this.nextStepId = nextStepId;
        this.stepStatus = stepStatus;
        this.stepTitle = stepTitle;
        this.stepType = stepType;
    }

    public String getCandidacyId() {
        return candidacyId;
    }

    public StatusCandidacy getCandidacyStatus() {
        return candidacyStatus;
    }

    public String getSelectiveProcessId() {
        return selectiveProcessId;
    }

    public String getSelectiveProcessTitle() {
        return selectiveProcessTitle;
    }

    public String getStepId() {
        return stepId;
    }

    public String getNextStepId() {
        return nextStepId;
    }

    public StatusStepCandidacy getStepStatus() {
        return stepStatus;
    }

    public String getStepTitle() {
        return stepTitle;
    }

    public TypeStep getStepType() {
        return stepType;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CandidacyVo that = (CandidacyVo) object;
        return Objects.equals(candidacyId, that.candidacyId) && candidacyStatus == that.candidacyStatus && Objects.equals(selectiveProcessId, that.selectiveProcessId) && Objects.equals(selectiveProcessTitle, that.selectiveProcessTitle) && Objects.equals(stepId, that.stepId) && Objects.equals(nextStepId, that.nextStepId) && stepStatus == that.stepStatus && Objects.equals(stepTitle, that.stepTitle) && stepType == that.stepType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidacyId, candidacyStatus, selectiveProcessId, selectiveProcessTitle, stepId, nextStepId, stepStatus, stepTitle, stepType);
    }

}

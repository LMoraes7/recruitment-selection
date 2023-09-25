package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.rowmapper.vo;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;

import java.util.Objects;

public final class SelectiveProcessStepsVo {
    private final String selectiveProcessIdentifier;
    private final StatusSelectiveProcess statusSelectiveProcess;
    private final String stepIdentifier;
    private final String nextStepIdentifier;
    private final Long stepLimitTime;
    private final TypeStep typeStep;

    public SelectiveProcessStepsVo(
            final String selectiveProcessIdentifier,
            final StatusSelectiveProcess statusSelectiveProcess,
            final String stepIdentifier,
            final String nextStepIdentifier,
            final Long stepLimitTime,
            final TypeStep typeStep
    ) {
        this.selectiveProcessIdentifier = selectiveProcessIdentifier;
        this.statusSelectiveProcess = statusSelectiveProcess;
        this.stepIdentifier = stepIdentifier;
        this.nextStepIdentifier = nextStepIdentifier;
        this.stepLimitTime = stepLimitTime;
        this.typeStep = typeStep;
    }

    public String getSelectiveProcessIdentifier() {
        return selectiveProcessIdentifier;
    }

    public StatusSelectiveProcess getStatusSelectiveProcess() {
        return statusSelectiveProcess;
    }

    public String getStepIdentifier() {
        return stepIdentifier;
    }

    public String getNextStepIdentifier() {
        return nextStepIdentifier;
    }

    public Long getStepLimitTime() {
        return stepLimitTime;
    }

    public TypeStep getTypeStep() {
        return typeStep;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SelectiveProcessStepsVo that = (SelectiveProcessStepsVo) object;
        return Objects.equals(selectiveProcessIdentifier, that.selectiveProcessIdentifier) && statusSelectiveProcess == that.statusSelectiveProcess && Objects.equals(stepIdentifier, that.stepIdentifier) && Objects.equals(nextStepIdentifier, that.nextStepIdentifier) && Objects.equals(stepLimitTime, that.stepLimitTime) && typeStep == that.typeStep;
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectiveProcessIdentifier, statusSelectiveProcess, stepIdentifier, nextStepIdentifier, stepLimitTime, typeStep);
    }

}

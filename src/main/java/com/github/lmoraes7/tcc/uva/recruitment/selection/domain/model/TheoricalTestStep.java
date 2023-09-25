package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class TheoricalTestStep implements StepSelectiveProcess, StepCandidacy {
    private StepData data;
    private List<Question> questions;
    private Long limitTime;
    private StatusStepCandidacy statusStepCandidacy;
    private LocalDate releaseData;

    public TheoricalTestStep(final StepData data, final List<Question> questions) {
        this.data = data;
        this.questions = questions;
    }

    @Override
    public StepData getData() {
        return data;
    }

    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    @Override
    public Long getLimitTime() {
        return limitTime;
    }

    @Override
    public StatusStepCandidacy getStatusStepCandidacy() {
        return statusStepCandidacy;
    }

    @Override
    public LocalDate getReleaseData() {
        return releaseData;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TheoricalTestStep that = (TheoricalTestStep) object;
        return Objects.equals(data, that.data) && Objects.equals(questions, that.questions) && Objects.equals(limitTime, that.limitTime) && statusStepCandidacy == that.statusStepCandidacy && Objects.equals(releaseData, that.releaseData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, questions, limitTime, statusStepCandidacy, releaseData);
    }

}

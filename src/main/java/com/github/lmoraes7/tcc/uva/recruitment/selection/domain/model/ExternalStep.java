package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public final class ExternalStep implements StepSelectiveProcess, StepCandidacy {
    private StepData data;
    private String activityLink;
    private LocalDateTime dateTime;
    private Long limitTime;
    private StatusStepCandidacy statusStepCandidacy;
    private LocalDate releaseData;

    public ExternalStep(final StepData data) {
        this.data = data;
    }

    public ExternalStep(final StepData data, final Long limitTime) {
        this.data = data;
        this.limitTime = limitTime;
    }

    public ExternalStep(
            final StepData data,
            final Long limitTime,
            final StatusStepCandidacy statusStepCandidacy
    ) {
        this.data = data;
        this.limitTime = limitTime;
        this.statusStepCandidacy = statusStepCandidacy;
    }

    public ExternalStep(
            final StepData data,
            final Long limitTime,
            final StatusStepCandidacy statusStepCandidacy,
            final LocalDate releaseData
    ) {
        this.data = data;
        this.limitTime = limitTime;
        this.statusStepCandidacy = statusStepCandidacy;
        this.releaseData = releaseData;
    }

    @Override
    public StepData getData() {
        return data;
    }

    public String getActivityLink() {
        return activityLink;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
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
        ExternalStep that = (ExternalStep) object;
        return Objects.equals(data, that.data) && Objects.equals(activityLink, that.activityLink) && Objects.equals(dateTime, that.dateTime) && Objects.equals(limitTime, that.limitTime) && statusStepCandidacy == that.statusStepCandidacy && Objects.equals(releaseData, that.releaseData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, activityLink, dateTime, limitTime, statusStepCandidacy, releaseData);
    }

}

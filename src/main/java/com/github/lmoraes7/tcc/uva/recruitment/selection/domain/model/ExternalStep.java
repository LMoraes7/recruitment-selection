package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;

import java.time.LocalDateTime;
import java.util.Objects;

public final class ExternalStep implements Step {
    private StepData data;
    private String activityLink;
    private LocalDateTime dateTime;

    public ExternalStep(final StepData data) {
        this.data = data;
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
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ExternalStep that = (ExternalStep) object;
        return Objects.equals(data, that.data) && Objects.equals(activityLink, that.activityLink) && Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, activityLink, dateTime);
    }

}

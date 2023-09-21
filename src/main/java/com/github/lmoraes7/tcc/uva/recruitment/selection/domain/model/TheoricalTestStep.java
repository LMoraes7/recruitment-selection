package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class TheoricalTestStep implements StepSelectiveProcess {
    private StepData data;
    private List<Question> questions;
    private Long limitTime;

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
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TheoricalTestStep that = (TheoricalTestStep) object;
        return Objects.equals(data, that.data) && Objects.equals(questions, that.questions) && Objects.equals(limitTime, that.limitTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, questions, limitTime);
    }

}

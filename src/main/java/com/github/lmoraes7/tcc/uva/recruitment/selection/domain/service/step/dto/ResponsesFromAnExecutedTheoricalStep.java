package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ResponsesFromAnExecutedTheoricalStep {
    private final List<ResponsesFromAnExecutedTheoricalQuestionStep> questionsExecuteds;

    public ResponsesFromAnExecutedTheoricalStep(final List<ResponsesFromAnExecutedTheoricalQuestionStep> questionsExecuteds) {
        this.questionsExecuteds = questionsExecuteds;
    }

    public List<ResponsesFromAnExecutedTheoricalQuestionStep> getQuestionsExecuteds() {
        return Collections.unmodifiableList(questionsExecuteds);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ResponsesFromAnExecutedTheoricalStep that = (ResponsesFromAnExecutedTheoricalStep) object;
        return Objects.equals(questionsExecuteds, that.questionsExecuteds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionsExecuteds);
    }

}

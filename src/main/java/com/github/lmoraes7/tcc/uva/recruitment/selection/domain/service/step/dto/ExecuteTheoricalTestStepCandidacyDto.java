package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ExecuteTheoricalTestStepCandidacyDto {
    private final List<ExecuteQuestionDto> questions;

    public ExecuteTheoricalTestStepCandidacyDto(final List<ExecuteQuestionDto> questions) {
        this.questions = questions;
    }

    public List<ExecuteQuestionDto> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ExecuteTheoricalTestStepCandidacyDto that = (ExecuteTheoricalTestStepCandidacyDto) object;
        return Objects.equals(questions, that.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questions);
    }

}

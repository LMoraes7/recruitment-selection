package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class SpecificTheoricalTestStepCandidacyDto {
    private final List<QuestionDto> questions;

    public SpecificTheoricalTestStepCandidacyDto(final List<QuestionDto> questions) {
        this.questions = questions;
    }

    public List<QuestionDto> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SpecificTheoricalTestStepCandidacyDto that = (SpecificTheoricalTestStepCandidacyDto) object;
        return Objects.equals(questions, that.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questions);
    }

}

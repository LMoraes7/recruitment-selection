package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public final class QuestionDto {
    private final String description;
    private final TypeQuestion type;
    private final Set<AnswerDto> answers;

    public QuestionDto(
            final String description,
            final TypeQuestion type,
            final Set<AnswerDto> answers
    ) {
        this.description = description;
        this.type = type;
        this.answers = answers;
    }

    public String getDescription() {
        return description;
    }

    public TypeQuestion getType() {
        return type;
    }

    public Set<AnswerDto> getAnswers() {
        return Collections.unmodifiableSet(answers);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        QuestionDto that = (QuestionDto) object;
        return Objects.equals(description, that.description) && type == that.type && Objects.equals(answers, that.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, type, answers);
    }

}

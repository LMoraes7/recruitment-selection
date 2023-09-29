package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;

import java.util.Objects;

public final class ExecuteQuestionDto {
    private final String questionIdentifier;
    private final TypeQuestion type;
    private final ExecuteAnswerDto answer;

    public ExecuteQuestionDto(final String questionIdentifier, final TypeQuestion type, final ExecuteAnswerDto answer) {
        this.questionIdentifier = questionIdentifier;
        this.type = type;
        this.answer = answer;
    }

    public String getQuestionIdentifier() {
        return questionIdentifier;
    }

    public TypeQuestion getType() {
        return type;
    }

    public ExecuteAnswerDto getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ExecuteQuestionDto that = (ExecuteQuestionDto) object;
        return Objects.equals(questionIdentifier, that.questionIdentifier) && type == that.type && Objects.equals(answer, that.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionIdentifier, type, answer);
    }

}

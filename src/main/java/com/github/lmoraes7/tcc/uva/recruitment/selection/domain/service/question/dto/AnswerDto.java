package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.dto;

import java.util.Objects;

public final class AnswerDto {
    private final String description;
    private final Boolean isCorrect;

    public AnswerDto(final String description, final Boolean isCorrect) {
        this.description = description;
        this.isCorrect = isCorrect;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AnswerDto answerDto = (AnswerDto) object;
        return Objects.equals(description, answerDto.description) && Objects.equals(isCorrect, answerDto.isCorrect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, isCorrect);
    }

}

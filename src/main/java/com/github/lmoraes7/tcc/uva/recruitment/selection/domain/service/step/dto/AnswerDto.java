package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import java.util.Objects;

public final class AnswerDto {
    private final String answerIdentifier;
    private final String answerDescription;

    public AnswerDto(final String answerIdentifier, final String answerDescription) {
        this.answerIdentifier = answerIdentifier;
        this.answerDescription = answerDescription;
    }

    public String getAnswerIdentifier() {
        return answerIdentifier;
    }

    public String getAnswerDescription() {
        return answerDescription;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AnswerDto answerDto = (AnswerDto) object;
        return Objects.equals(answerIdentifier, answerDto.answerIdentifier) && Objects.equals(answerDescription, answerDto.answerDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerIdentifier, answerDescription);
    }

}

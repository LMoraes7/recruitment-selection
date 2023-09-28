package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class QuestionDto {
    private final String questionIdentifier;
    private final String questionDescription;
    private final TypeQuestion questionType;
    private final List<AnswerDto> answers;

    public QuestionDto(
            final String questionIdentifier,
            final String questionDescription,
            final TypeQuestion questionType,
            final List<AnswerDto> answers
    ) {
        this.questionIdentifier = questionIdentifier;
        this.questionDescription = questionDescription;
        this.questionType = questionType;
        this.answers = answers;
    }

    public String getQuestionIdentifier() {
        return questionIdentifier;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public TypeQuestion getQuestionType() {
        return questionType;
    }

    public List<AnswerDto> getAnswers() {
        return Collections.unmodifiableList(answers);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        QuestionDto that = (QuestionDto) object;
        return Objects.equals(questionIdentifier, that.questionIdentifier) && Objects.equals(questionDescription, that.questionDescription) && questionType == that.questionType && Objects.equals(answers, that.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionIdentifier, questionDescription, questionType, answers);
    }

}

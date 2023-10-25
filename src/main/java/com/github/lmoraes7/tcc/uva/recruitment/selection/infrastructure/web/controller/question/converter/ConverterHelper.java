package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.question.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.dto.AnswerDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.dto.QuestionDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.question.request.AnswerRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.question.request.QuestionDiscursiveRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.question.request.QuestionMultipleChoiceRequest;

import java.util.Set;
import java.util.stream.Collectors;

public final class ConverterHelper {

    public static QuestionDto toDto(final QuestionDiscursiveRequest request) {
        return new QuestionDto(
                request.getDescription(),
                TypeQuestion.DISCURSIVE,
                null
        );
    }

    public static QuestionDto toDto(final QuestionMultipleChoiceRequest request) {
        return new QuestionDto(
                request.getDescription(),
                TypeQuestion.MULTIPLE_CHOICE,
                toDto(request.getAnswers())
        );
    }

    private static Set<AnswerDto> toDto(final Set<AnswerRequest> answerRequests) {
        return answerRequests.stream().map(it -> new AnswerDto(it.getDescription(), it.getCorrect())).collect(Collectors.toSet());
    }

}

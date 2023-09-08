package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Answer;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.dto.AnswerDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.dto.QuestionDto;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public final class ConverterHelper {

    public static Question toModel(final QuestionDto dto) {
        return new Question(
                GeneratorIdentifier.forQuestion(),
                dto.getDescription(),
                dto.getType(),
                toModel(dto, dto.getAnswers())
        );
    }

    private static Set<Answer> toModel(final QuestionDto questionDto, final Collection<AnswerDto> answerDtos) {
        if (questionDto.getType() != TypeQuestion.MULTIPLE_CHOICE)
            return null;

        return answerDtos.stream().map(it -> new Answer(
                GeneratorIdentifier.forAnswer(),
                it.getDescription(),
                it.getCorrect()
        )).collect(Collectors.toSet());
    }

}

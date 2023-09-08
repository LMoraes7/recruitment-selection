package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Answer;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer.entity.AnswerEntity;

import java.util.Collection;
import java.util.List;

public final class ConverterHelper {

    public static List<AnswerEntity> toEntity(final String questionIdentifier, final Collection<Answer> answers) {
        return answers.stream().map(it ->
                new AnswerEntity(
                        it.getIdentifier(),
                        it.getDescription(),
                        it.getCorrect(),
                        questionIdentifier
                )
        ).toList();
    }

}

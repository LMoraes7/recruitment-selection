package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.entity.QuestionEntity;

public final class ConverterHelper {

    public static QuestionEntity toEntity(final Question question) {
        return new QuestionEntity(
                question.getIdentifier(),
                question.getDescription(),
                question.getType().name()
        );
    }

}

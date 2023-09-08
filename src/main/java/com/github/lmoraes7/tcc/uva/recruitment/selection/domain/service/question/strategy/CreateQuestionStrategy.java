package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.strategy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.dto.QuestionDto;

public interface CreateQuestionStrategy {
    TypeQuestion getTypeQuestion();

    Question execute(final QuestionDto dto);
}

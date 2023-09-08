package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.strategy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.dto.QuestionDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class CreateQuestionDiscursive implements CreateQuestionStrategy {

    private final QuestionRepository questionRepository;

    @Autowired
    public CreateQuestionDiscursive(final QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public TypeQuestion getTypeQuestion() {
        return TypeQuestion.DISCURSIVE;
    }

    @Override
    public Question execute(final QuestionDto dto) {
        return this.questionRepository.saveWithoutAnswers(ConverterHelper.toModel(dto));
    }

}

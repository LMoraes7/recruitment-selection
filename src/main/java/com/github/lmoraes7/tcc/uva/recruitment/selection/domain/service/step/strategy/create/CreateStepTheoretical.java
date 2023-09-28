package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.create;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Step;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.StepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.QuestionRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.TheoricalTestlStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_008;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_009;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.utils.CommonFunctions.validateIdentifiers;

@Service
public final class CreateStepTheoretical implements CreateStepStrategy {
    private final TheoricalTestlStepRepository theoricalTestlStepRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public CreateStepTheoretical(
            final TheoricalTestlStepRepository theoricalTestlStepRepository,
            final QuestionRepository questionRepository
    ) {
        this.theoricalTestlStepRepository = theoricalTestlStepRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public TypeStep getTypeStep() {
        return TypeStep.THEORETICAL_TEST;
    }

    @Override
    public Step execute(final StepDto dto) {
        final Collection<Question> questions = this.questionRepository.fetchQuestion(dto.getQuestionsIdentifiers());

        final Set<String> invalidIdentifiers = validateIdentifiers(
                questions.stream().map(Question::getIdentifier).collect(Collectors.toSet()),
                dto.getQuestionsIdentifiers()
        );

        if (!invalidIdentifiers.isEmpty())
            throw new BusinessException(APIX_008, invalidIdentifiers);

        questions.stream().findFirst().ifPresent(
                question -> {
                    if (!questions.stream().filter(it -> it.getType() != question.getType()).toList().isEmpty())
                        throw new BusinessException(APIX_009);
                }
        );

        return this.theoricalTestlStepRepository.save(ConverterHelper.toTheoricalTestStepModel(dto));
    }

}

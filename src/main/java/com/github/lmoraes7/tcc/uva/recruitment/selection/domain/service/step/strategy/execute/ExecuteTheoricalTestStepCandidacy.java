package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.execute;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteQuestionDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.QuestionRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.TheoricalTestStepCandidacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_008;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_015;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.utils.CommonFunctions.validateIdentifiers;

@Component
public final class ExecuteTheoricalTestStepCandidacy implements ExecuteStepCandidacyStrategy {
    private final QuestionRepository questionRepository;
    private final TheoricalTestStepCandidacyRepository theoricalTestStepCandidacyRepository;

    @Autowired
    public ExecuteTheoricalTestStepCandidacy(
            final QuestionRepository questionRepository,
            final TheoricalTestStepCandidacyRepository theoricalTestStepCandidacyRepository
    ) {
        this.questionRepository = questionRepository;
        this.theoricalTestStepCandidacyRepository = theoricalTestStepCandidacyRepository;
    }

    @Override
    public TypeStep getTypeStep() {
        return TypeStep.THEORETICAL_TEST;
    }

    @Override
    public void execute(final String candidateIdentifier, final ExecuteStepCandidacyDto dto) {
        final List<ExecuteQuestionDto> questions = dto.getTheoricalTest().getQuestions();

        valideUniqueTypeQuestion(questions);
        valideQuestionsIdentifiers(questions);

        this.theoricalTestStepCandidacyRepository.saveTestExecuted(
                candidateIdentifier,
                dto.getCandidacyIdentifier(),
                dto.getTheoricalTest()
        );
    }

    private void valideQuestionsIdentifiers(List<ExecuteQuestionDto> questions) {
        final List<String> questionsIdentifiersToValidate = questions
                .stream()
                .map(ExecuteQuestionDto::getQuestionIdentifier)
                .toList();

        final Collection<Question> validQuestions = this.questionRepository.fetchQuestion(questionsIdentifiersToValidate);

        final Set<String> invalidIdentifiers = validateIdentifiers(
                validQuestions.stream().map(Question::getIdentifier).toList(),
                questionsIdentifiersToValidate
        );

        if (!invalidIdentifiers.isEmpty())
            throw new BusinessException(APIX_008, invalidIdentifiers);
    }

    private void valideUniqueTypeQuestion(List<ExecuteQuestionDto> questions) {
        if (questions.stream().map(ExecuteQuestionDto::getType).collect(Collectors.toSet()).size() != 1)
            throw new BusinessException(APIX_015);
    }

}

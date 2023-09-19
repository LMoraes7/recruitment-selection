package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.TheoricalTestStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.StepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.QuestionRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.TheoricalTestlStepRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_008;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_009;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class CreateStepTheoreticalTest {
    private final TheoricalTestlStepRepository theoricalTestlStepRepository = mock(TheoricalTestlStepRepository.class);
    private final QuestionRepository questionRepository = mock(QuestionRepository.class);
    private final CreateStepTheoretical createStepTheoretical = new CreateStepTheoretical(this.theoricalTestlStepRepository, this.questionRepository);

    private StepDto stepDto;
    private List<Question> questions;
    private TheoricalTestStep theoricalTestStep;

    @BeforeEach
    void setUp() {
        this.stepDto = new StepDto(
                "title",
                "description",
                TypeStep.THEORETICAL_TEST,
                Set.of(
                        GeneratorIdentifier.forQuestion(),
                        GeneratorIdentifier.forQuestion(),
                        GeneratorIdentifier.forQuestion(),
                        GeneratorIdentifier.forQuestion()
                ),
                null
        );
        this.questions = this.stepDto.getQuestionsIdentifiers().stream().map(it -> new Question(it, TypeQuestion.DISCURSIVE)).collect(Collectors.toList());
        this.theoricalTestStep = dummyObject(TheoricalTestStep.class);
    }

    @Test
    void when_prompted_should_create_a_step_successfully() {
        when(this.questionRepository.fetchQuestion(this.stepDto.getQuestionsIdentifiers())).thenReturn(this.questions);
        when(this.theoricalTestlStepRepository.save(any(TheoricalTestStep.class))).thenReturn(this.theoricalTestStep);

        assertDoesNotThrow(() -> this.createStepTheoretical.execute(this.stepDto));

        verify(this.questionRepository, only()).fetchQuestion(this.stepDto.getQuestionsIdentifiers());
        verify(this.theoricalTestlStepRepository, only()).save(any(TheoricalTestStep.class));
    }

    @Test
    void when_requested_it_must_throw_a_BusinessException_when_it_has_invalid_issue_identifiers() {
        this.questions.remove(0);

        when(this.questionRepository.fetchQuestion(this.stepDto.getQuestionsIdentifiers())).thenReturn(this.questions);

        final BusinessException businessException = assertThrows(
                BusinessException.class,
                () -> this.createStepTheoretical.execute(this.stepDto)
        );

        assertEquals(businessException.getError(), APIX_008);

        verify(this.questionRepository, only()).fetchQuestion(this.stepDto.getQuestionsIdentifiers());
        verifyNoInteractions(this.theoricalTestlStepRepository);
    }

    @Test
    void when_requested_it_must_throw_a_BusinessException_when_searching_for_questions_of_different_types() {
        this.questions.set(2, new Question(this.questions.get(2).getIdentifier(), TypeQuestion.MULTIPLE_CHOICE));

        when(this.questionRepository.fetchQuestion(this.stepDto.getQuestionsIdentifiers())).thenReturn(this.questions);

        final BusinessException businessException = assertThrows(
                BusinessException.class,
                () -> this.createStepTheoretical.execute(this.stepDto)
        );

        assertEquals(businessException.getError(), APIX_009);

        verify(this.questionRepository, only()).fetchQuestion(this.stepDto.getQuestionsIdentifiers());
        verifyNoInteractions(this.theoricalTestlStepRepository);
    }

    @Test
    void when_requested_it_must_return_the_type_of_step_to_be_treated() {
        assertDoesNotThrow(() -> assertEquals(TypeStep.THEORETICAL_TEST, this.createStepTheoretical.getTypeStep()));

        verifyNoInteractions(this.questionRepository, this.theoricalTestlStepRepository);
    }

}
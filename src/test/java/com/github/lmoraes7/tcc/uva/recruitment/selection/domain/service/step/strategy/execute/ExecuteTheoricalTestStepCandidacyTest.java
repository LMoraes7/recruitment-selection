package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.execute;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteAnswerDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteQuestionDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteTheoricalTestStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.QuestionRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.TheoricalTestStepCandidacyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_008;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_015;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class ExecuteTheoricalTestStepCandidacyTest {
    private final QuestionRepository questionRepository = mock(QuestionRepository.class);
    private final TheoricalTestStepCandidacyRepository theoricalTestStepCandidacyRepository = mock(TheoricalTestStepCandidacyRepository.class);
    private final ExecuteTheoricalTestStepCandidacy executeTheoricalTestStepCandidacy = new ExecuteTheoricalTestStepCandidacy(this.questionRepository, this.theoricalTestStepCandidacyRepository);

    private List<Question> questions;
    private String candidateIdentifier;
    private ExecuteStepCandidacyDto executeStepCandidacyDto;

    @BeforeEach
    void setUp() {
        this.questions = List.of(
                new Question(GeneratorIdentifier.forQuestion()),
                new Question(GeneratorIdentifier.forQuestion()),
                new Question(GeneratorIdentifier.forQuestion()),
                new Question(GeneratorIdentifier.forQuestion())
        );
        this.candidateIdentifier = GeneratorIdentifier.forCandidate();
        this.executeStepCandidacyDto = new ExecuteStepCandidacyDto(
                GeneratorIdentifier.forStep(),
                GeneratorIdentifier.forCandidacy(),
                GeneratorIdentifier.forSelectiveProcess(),
                TypeStep.THEORETICAL_TEST,
                new ExecuteTheoricalTestStepCandidacyDto(
                        List.of(
                                new ExecuteQuestionDto(
                                        this.questions.get(0).getIdentifier(),
                                        TypeQuestion.MULTIPLE_CHOICE,
                                        new ExecuteAnswerDto(GeneratorIdentifier.forAnswer())
                                ),
                                new ExecuteQuestionDto(
                                        this.questions.get(1).getIdentifier(),
                                        TypeQuestion.MULTIPLE_CHOICE,
                                        new ExecuteAnswerDto(GeneratorIdentifier.forAnswer())
                                ),
                                new ExecuteQuestionDto(
                                        this.questions.get(2).getIdentifier(),
                                        TypeQuestion.MULTIPLE_CHOICE,
                                        new ExecuteAnswerDto(GeneratorIdentifier.forAnswer())
                                ),
                                new ExecuteQuestionDto(
                                        this.questions.get(3).getIdentifier(),
                                        TypeQuestion.MULTIPLE_CHOICE,
                                        new ExecuteAnswerDto(GeneratorIdentifier.forAnswer())
                                )
                        )
                ),
                null
        );
    }

    @Test
    void when_prompted_must_execute_successfully() {
        final List<String> questionsIdentifiersToValidate = executeStepCandidacyDto.getTheoricalTest().getQuestions()
                .stream()
                .map(ExecuteQuestionDto::getQuestionIdentifier)
                .toList();

        when(this.questionRepository.fetchQuestion(
                questionsIdentifiersToValidate
        )).thenReturn(this.questions);

        assertDoesNotThrow(() -> this.executeTheoricalTestStepCandidacy.execute(this.candidateIdentifier, this.executeStepCandidacyDto));

        verify(this.questionRepository, only()).fetchQuestion(
                questionsIdentifiersToValidate
        );
        verify(this.theoricalTestStepCandidacyRepository, only()).saveTestExecuted(
                this.candidateIdentifier,
                this.executeStepCandidacyDto.getCandidacyIdentifier(),
                this.executeStepCandidacyDto.getTheoricalTest()
        );
    }

    @Test
    void when_requested_it_must_throw_a_BusinessException_when_questions_are_of_different_types() {
        this.executeStepCandidacyDto = new ExecuteStepCandidacyDto(
                GeneratorIdentifier.forStep(),
                GeneratorIdentifier.forCandidacy(),
                GeneratorIdentifier.forSelectiveProcess(),
                TypeStep.THEORETICAL_TEST,
                new ExecuteTheoricalTestStepCandidacyDto(
                        List.of(
                                new ExecuteQuestionDto(
                                        this.questions.get(0).getIdentifier(),
                                        TypeQuestion.MULTIPLE_CHOICE,
                                        new ExecuteAnswerDto(GeneratorIdentifier.forAnswer())
                                ),
                                new ExecuteQuestionDto(
                                        this.questions.get(1).getIdentifier(),
                                        TypeQuestion.DISCURSIVE,
                                        new ExecuteAnswerDto(GeneratorIdentifier.forAnswer())
                                )
                        )
                ),
                null
        );

        final BusinessException businessException = assertThrows(
                BusinessException.class,
                () -> this.executeTheoricalTestStepCandidacy.execute(this.candidateIdentifier, this.executeStepCandidacyDto)
        );

        assertEquals(businessException.getError(), APIX_015);

        verifyNoInteractions(this.questionRepository, this.theoricalTestStepCandidacyRepository);
    }

    @Test
    void when_requested_it_must_throw_a_BusinessException_when_non_existent_issues_are_reported() {
        final List<String> questionsIdentifiersToValidate = executeStepCandidacyDto.getTheoricalTest().getQuestions()
                .stream()
                .map(ExecuteQuestionDto::getQuestionIdentifier)
                .toList();

        when(this.questionRepository.fetchQuestion(
                questionsIdentifiersToValidate
        )).thenReturn(List.of());

        final BusinessException businessException = assertThrows(
                BusinessException.class,
                () -> this.executeTheoricalTestStepCandidacy.execute(this.candidateIdentifier, this.executeStepCandidacyDto)
        );

        assertEquals(businessException.getError(), APIX_008);

        verify(this.questionRepository, only()).fetchQuestion(
                questionsIdentifiersToValidate
        );
        verifyNoInteractions(this.theoricalTestStepCandidacyRepository);
    }

}
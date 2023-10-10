package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.*;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.batch.SaveAnswerMultipleChoiceTheoricalTestStepBatch;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.TheoricalTestStepCandidacyRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo.TheoricalTestStepCandidacyVo;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.vo.StepBatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.*;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.*;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class TheoricalTestStepCandidacyRepositoryTest {
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final TheoricalTestStepCandidacyRowMapper theoricalTestStepCandidacyRowMapper = mock(TheoricalTestStepCandidacyRowMapper.class);
    private final TheoricalTestStepCandidacyRepository theoricalTestStepCandidacyRepository = new TheoricalTestStepCandidacyRepository(this.jdbcTemplate, this.theoricalTestStepCandidacyRowMapper);

    private String candidacyIdentifier;
    private String candidateIdentifier;
    private String selectiveProcessIdentifier;
    private String stepIdentifier;
    private List<String> questionsIdentifiers;
    private List<String> answersIdentifiers;
    private List<TheoricalTestStepCandidacyVo> theoricalTestStepCandidacyVos;
    private ExecuteTheoricalTestStepCandidacyDto theoricalTest;
    private List<ResponsesFromAnExecutedTheoricalQuestionStep> questionsDirscusiveExecuted;
    private List<ResponsesFromAnExecutedTheoricalQuestionStep> questionsMultipleChoiseExecuted;

    @BeforeEach
    void setUp() {
        this.candidacyIdentifier = GeneratorIdentifier.forCandidacy();
        this.candidateIdentifier = GeneratorIdentifier.forCandidate();
        this.selectiveProcessIdentifier = GeneratorIdentifier.forSelectiveProcess();
        this.stepIdentifier = GeneratorIdentifier.forStep();
        this.questionsIdentifiers = List.of(
                "QUE-123456781",
                "QUE-123456782",
                "QUE-123456783"
        );
        this.answersIdentifiers = List.of(
                "ANS-123456781",
                "ANS-123456782",
                "ANS-123456783",
                "ANS-123456784",
                "ANS-123456785",
                "ANS-123456786"
        );
        this.theoricalTestStepCandidacyVos = Arrays.asList(
                new TheoricalTestStepCandidacyVo(
                        this.candidacyIdentifier,
                        this.candidateIdentifier,
                        this.selectiveProcessIdentifier,
                        this.stepIdentifier,
                        this.questionsIdentifiers.get(1),
                        dummyObject(String.class),
                        TypeQuestion.MULTIPLE_CHOICE,
                        this.answersIdentifiers.get(3),
                        dummyObject(String.class)
                ),
                new TheoricalTestStepCandidacyVo(
                        this.candidacyIdentifier,
                        this.candidateIdentifier,
                        this.selectiveProcessIdentifier,
                        this.stepIdentifier,
                        this.questionsIdentifiers.get(0),
                        dummyObject(String.class),
                        TypeQuestion.MULTIPLE_CHOICE,
                        this.answersIdentifiers.get(5),
                        dummyObject(String.class)
                ),
                new TheoricalTestStepCandidacyVo(
                        this.candidacyIdentifier,
                        this.candidateIdentifier,
                        this.selectiveProcessIdentifier,
                        this.stepIdentifier,
                        this.questionsIdentifiers.get(2),
                        dummyObject(String.class),
                        TypeQuestion.MULTIPLE_CHOICE,
                        this.answersIdentifiers.get(1),
                        dummyObject(String.class)
                ),
                new TheoricalTestStepCandidacyVo(
                        this.candidacyIdentifier,
                        this.candidateIdentifier,
                        this.selectiveProcessIdentifier,
                        this.stepIdentifier,
                        this.questionsIdentifiers.get(2),
                        dummyObject(String.class),
                        TypeQuestion.MULTIPLE_CHOICE,
                        this.answersIdentifiers.get(0),
                        dummyObject(String.class)
                ),
                new TheoricalTestStepCandidacyVo(
                        this.candidacyIdentifier,
                        this.candidateIdentifier,
                        this.selectiveProcessIdentifier,
                        this.stepIdentifier,
                        this.questionsIdentifiers.get(1),
                        dummyObject(String.class),
                        TypeQuestion.MULTIPLE_CHOICE,
                        this.answersIdentifiers.get(2),
                        dummyObject(String.class)
                ),
                new TheoricalTestStepCandidacyVo(
                        this.candidacyIdentifier,
                        this.candidateIdentifier,
                        this.selectiveProcessIdentifier,
                        this.stepIdentifier,
                        this.questionsIdentifiers.get(0),
                        dummyObject(String.class),
                        TypeQuestion.MULTIPLE_CHOICE,
                        this.answersIdentifiers.get(4),
                        dummyObject(String.class)
                )
        );

        this.theoricalTest = new ExecuteTheoricalTestStepCandidacyDto(
                List.of(
                        new ExecuteQuestionDto(
                                GeneratorIdentifier.forQuestion(),
                                TypeQuestion.MULTIPLE_CHOICE,
                                new ExecuteAnswerDto(GeneratorIdentifier.forAnswer())
                        ),
                        new ExecuteQuestionDto(
                                GeneratorIdentifier.forQuestion(),
                                TypeQuestion.MULTIPLE_CHOICE,
                                new ExecuteAnswerDto(GeneratorIdentifier.forAnswer())
                        ),
                        new ExecuteQuestionDto(
                                GeneratorIdentifier.forQuestion(),
                                TypeQuestion.MULTIPLE_CHOICE,
                                new ExecuteAnswerDto(GeneratorIdentifier.forAnswer())
                        ),
                        new ExecuteQuestionDto(
                                GeneratorIdentifier.forQuestion(),
                                TypeQuestion.MULTIPLE_CHOICE,
                                new ExecuteAnswerDto(GeneratorIdentifier.forAnswer())
                        )
                )
        );

        this.questionsDirscusiveExecuted = List.of(
                new ResponsesFromAnExecutedTheoricalQuestionStep(
                        GeneratorIdentifier.forQuestion(),
                        null,
                        TypeQuestion.DISCURSIVE,
                        dummyObject(String.class),
                        null,
                        dummyObject(String.class),
                        null
                ),
                new ResponsesFromAnExecutedTheoricalQuestionStep(
                        GeneratorIdentifier.forQuestion(),
                        null,
                        TypeQuestion.DISCURSIVE,
                        dummyObject(String.class),
                        null,
                        dummyObject(String.class),
                        null
                ),
                new ResponsesFromAnExecutedTheoricalQuestionStep(
                        GeneratorIdentifier.forQuestion(),
                        null,
                        TypeQuestion.DISCURSIVE,
                        dummyObject(String.class),
                        null,
                        dummyObject(String.class),
                        null
                )
        );

        this.questionsMultipleChoiseExecuted = List.of(
                new ResponsesFromAnExecutedTheoricalQuestionStep(
                        GeneratorIdentifier.forQuestion(),
                        GeneratorIdentifier.forAnswer(),
                        TypeQuestion.MULTIPLE_CHOICE,
                        dummyObject(String.class),
                        dummyObject(String.class),
                        null,
                        true
                ),
                new ResponsesFromAnExecutedTheoricalQuestionStep(
                        GeneratorIdentifier.forQuestion(),
                        GeneratorIdentifier.forAnswer(),
                        TypeQuestion.MULTIPLE_CHOICE,
                        dummyObject(String.class),
                        dummyObject(String.class),
                        null,
                        false
                ),
                new ResponsesFromAnExecutedTheoricalQuestionStep(
                        GeneratorIdentifier.forQuestion(),
                        GeneratorIdentifier.forAnswer(),
                        TypeQuestion.MULTIPLE_CHOICE,
                        dummyObject(String.class),
                        dummyObject(String.class),
                        null,
                        true
                )
        );
    }

    @Test
    void when_requested_must_consult_a_theoretical_step_successfully() {
        for (int i = 0; i < 10; i++) {
            Collections.shuffle(this.theoricalTestStepCandidacyVos);

            when(this.jdbcTemplate.query(
                    FIND_QUESTIONS_TO_BE_EXECUTED.sql,
                    this.theoricalTestStepCandidacyRowMapper,
                    this.candidacyIdentifier,
                    this.candidateIdentifier,
                    this.selectiveProcessIdentifier,
                    this.stepIdentifier
            )).thenReturn(this.theoricalTestStepCandidacyVos);

            assertDoesNotThrow(() -> {
                final Optional<SpecificExecutionStepCandidacyDto> optional = this.theoricalTestStepCandidacyRepository.findQuestions(
                        this.candidacyIdentifier,
                        this.candidateIdentifier,
                        this.selectiveProcessIdentifier,
                        this.stepIdentifier
                );

                assertNotNull(optional);
                assertTrue(optional.isPresent());

                final SpecificExecutionStepCandidacyDto step = optional.get();

                assertEquals(step.getTheoricalTestStep().getQuestions().size(), 3);

                QuestionDto first = step.getTheoricalTestStep().getQuestions()
                        .stream()
                        .filter(it -> Objects.equals(it.getQuestionIdentifier(), this.questionsIdentifiers.get(0)))
                        .findFirst().get();
                assertEquals(first.getAnswers().size(), 2);
                assertEquals(
                        first.getAnswers().stream().filter(it -> Objects.equals(it.getAnswerIdentifier(), this.answersIdentifiers.get(4)) || Objects.equals(it.getAnswerIdentifier(), this.answersIdentifiers.get(5))).toList().size(),
                        2
                );

                first = step.getTheoricalTestStep().getQuestions()
                        .stream()
                        .filter(it -> Objects.equals(it.getQuestionIdentifier(), this.questionsIdentifiers.get(1)))
                        .findFirst().get();
                assertEquals(first.getAnswers().size(), 2);
                assertEquals(
                        first.getAnswers().stream().filter(it -> Objects.equals(it.getAnswerIdentifier(), this.answersIdentifiers.get(2)) || Objects.equals(it.getAnswerIdentifier(), this.answersIdentifiers.get(3))).toList().size(),
                        2
                );

                first = step.getTheoricalTestStep().getQuestions()
                        .stream()
                        .filter(it -> Objects.equals(it.getQuestionIdentifier(), this.questionsIdentifiers.get(2)))
                        .findFirst().get();
                assertEquals(first.getAnswers().size(), 2);
                assertEquals(
                        first.getAnswers().stream().filter(it -> Objects.equals(it.getAnswerIdentifier(), this.answersIdentifiers.get(0)) || Objects.equals(it.getAnswerIdentifier(), this.answersIdentifiers.get(1))).toList().size(),
                        2
                );
            });
        }

        verify(this.jdbcTemplate, times(10)).query(
                FIND_QUESTIONS_TO_BE_EXECUTED.sql,
                this.theoricalTestStepCandidacyRowMapper,
                this.candidacyIdentifier,
                this.candidateIdentifier,
                this.selectiveProcessIdentifier,
                this.stepIdentifier
        );
        verifyNoMoreInteractions(this.jdbcTemplate);
    }

    @Test
    void when_prompted_must_fail_to_query_a_theoretical_step() {
        when(this.jdbcTemplate.query(
                FIND_QUESTIONS_TO_BE_EXECUTED.sql,
                this.theoricalTestStepCandidacyRowMapper,
                this.candidacyIdentifier,
                this.candidateIdentifier,
                this.selectiveProcessIdentifier,
                this.stepIdentifier
        )).thenReturn(List.of());

        assertDoesNotThrow(() -> {
                    final Optional<SpecificExecutionStepCandidacyDto> optional = this.theoricalTestStepCandidacyRepository.findQuestions(
                            this.candidacyIdentifier,
                            this.candidateIdentifier,
                            this.selectiveProcessIdentifier,
                            this.stepIdentifier
                    );

                    assertNotNull(optional);
                    assertTrue(optional.isEmpty());
                }
        );

        verify(this.jdbcTemplate, only()).query(
                FIND_QUESTIONS_TO_BE_EXECUTED.sql,
                this.theoricalTestStepCandidacyRowMapper,
                this.candidacyIdentifier,
                this.candidateIdentifier,
                this.selectiveProcessIdentifier,
                this.stepIdentifier
        );
    }

    @Test
    void when_prompted_it_should_save_responses_successfully() {
        final List<StepBatch> stepBatches = theoricalTest.getQuestions().stream().map(it -> {
            String answerIdentifier = null;
            String answerDiscursive = null;

            if (it.getType() == TypeQuestion.MULTIPLE_CHOICE)
                answerIdentifier = it.getAnswer().getAnswer();
            else
                answerDiscursive = it.getAnswer().getAnswer();

            return new StepBatch(
                    candidacyIdentifier,
                    stepIdentifier,
                    it.getQuestionIdentifier(),
                    answerIdentifier,
                    it.getType(),
                    answerDiscursive
            );
        }).toList();

        when(this.jdbcTemplate.batchUpdate(
                SAVE_EXECUTION_QUESTION_MULTIPLE_CHOICE.sql,
                new SaveAnswerMultipleChoiceTheoricalTestStepBatch(stepBatches)
        )).thenReturn(new int[]{0});

        assertDoesNotThrow(() -> this.theoricalTestStepCandidacyRepository.saveTestExecuted(
                this.candidacyIdentifier,
                this.stepIdentifier,
                this.theoricalTest
        ));

        verify(this.jdbcTemplate, only()).batchUpdate(
                SAVE_EXECUTION_QUESTION_MULTIPLE_CHOICE.sql,
                new SaveAnswerMultipleChoiceTheoricalTestStepBatch(stepBatches)
        );
    }

    @Test
    void when_requested_you_must_query_the_questions_successfully() {
        when(this.jdbcTemplate.query(
                eq(FIND_QUESTIONS_EXECUTEDS.sql),
                ArgumentMatchers.<RowMapper<ResponsesFromAnExecutedTheoricalQuestionStep>>any(),
                eq(this.candidacyIdentifier),
                eq(this.stepIdentifier)
        )).thenReturn(this.questionsDirscusiveExecuted);

        assertDoesNotThrow(() -> this.theoricalTestStepCandidacyRepository.consultTestExecuted(this.candidacyIdentifier, this.stepIdentifier));

        verify(this.jdbcTemplate, only()).query(
                eq(FIND_QUESTIONS_EXECUTEDS.sql),
                ArgumentMatchers.<RowMapper<ResponsesFromAnExecutedTheoricalQuestionStep>>any(),
                eq(this.candidacyIdentifier),
                eq(this.stepIdentifier)
        );
    }

    @Test
    void when_requested_you_must_query_the_questions_successfully_2() {
        when(this.jdbcTemplate.query(
                eq(FIND_QUESTIONS_EXECUTEDS.sql),
                ArgumentMatchers.<RowMapper<ResponsesFromAnExecutedTheoricalQuestionStep>>any(),
                eq(this.candidacyIdentifier),
                eq(this.stepIdentifier)
        )).thenReturn(this.questionsMultipleChoiseExecuted);

        assertDoesNotThrow(() -> this.theoricalTestStepCandidacyRepository.consultTestExecuted(this.candidacyIdentifier, this.stepIdentifier));

        verify(this.jdbcTemplate, only()).query(
                eq(FIND_QUESTIONS_EXECUTEDS.sql),
                ArgumentMatchers.<RowMapper<ResponsesFromAnExecutedTheoricalQuestionStep>>any(),
                eq(this.candidacyIdentifier),
                eq(this.stepIdentifier)
        );
    }

}
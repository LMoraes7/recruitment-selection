package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.QuestionDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.SpecificExecutionStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.TheoricalTestStepCandidacyRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo.TheoricalTestStepCandidacyVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.FIND_QUESTIONS_TO_BE_EXECUTED;
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

}
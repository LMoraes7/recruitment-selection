package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.SpecificExecutionStepCandidacyDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
@SpringBootTest
class TheoricalTestStepCandidacyRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TheoricalTestStepCandidacyRepository theoricalTestStepCandidacyRepository;

    private String candidacyIdentifier;
    private String candidateIdentifier;
    private String selectiveProcessIdentifier;
    private String stepIdentifierMultipleChoise;
    private String stepIdentifierDiscursive;

    @BeforeEach
    void setUp() {
        this.candidacyIdentifier = "APP-123456781";
        this.candidateIdentifier = "CAN-123456781";
        this.selectiveProcessIdentifier = "SEL-123456781";
        this.stepIdentifierMultipleChoise = "STE-123456781";
        this.stepIdentifierDiscursive = "STE-123456782";
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/theorical_test_step_candidacy_repository_test.sql"})
    void when_requested_must_consult_a_theoretical_step_successfully_1() {
        assertDoesNotThrow(() -> {
            final Optional<SpecificExecutionStepCandidacyDto> optional = this.theoricalTestStepCandidacyRepository.findQuestions(
                    this.candidacyIdentifier,
                    this.candidateIdentifier,
                    this.selectiveProcessIdentifier,
                    this.stepIdentifierMultipleChoise
            );

            assertNotNull(optional);
            assertTrue(optional.isPresent());
        });
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/theorical_test_step_candidacy_repository_test.sql"})
    void when_requested_must_consult_a_theoretical_step_successfully_2() {
        assertDoesNotThrow(() -> {
            final Optional<SpecificExecutionStepCandidacyDto> optional = this.theoricalTestStepCandidacyRepository.findQuestions(
                    this.candidacyIdentifier,
                    this.candidateIdentifier,
                    this.selectiveProcessIdentifier,
                    this.stepIdentifierDiscursive
            );

            assertNotNull(optional);
            assertTrue(optional.isPresent());
        });
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/theorical_test_step_candidacy_repository_test.sql"})
    void when_prompted_must_fail_to_query_a_theoretical_step_1() {
        assertDoesNotThrow(() -> {
                    final Optional<SpecificExecutionStepCandidacyDto> optional = this.theoricalTestStepCandidacyRepository.findQuestions(
                            GeneratorIdentifier.forCandidacy(),
                            this.candidateIdentifier,
                            this.selectiveProcessIdentifier,
                            this.stepIdentifierMultipleChoise
                    );

                    assertNotNull(optional);
                    assertTrue(optional.isEmpty());
                }
        );
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/theorical_test_step_candidacy_repository_test.sql"})
    void when_prompted_must_fail_to_query_a_theoretical_step_2() {
        assertDoesNotThrow(() -> {
                    final Optional<SpecificExecutionStepCandidacyDto> optional = this.theoricalTestStepCandidacyRepository.findQuestions(
                            this.candidacyIdentifier,
                            GeneratorIdentifier.forCandidate(),
                            this.selectiveProcessIdentifier,
                            this.stepIdentifierMultipleChoise
                    );

                    assertNotNull(optional);
                    assertTrue(optional.isEmpty());
                }
        );
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/theorical_test_step_candidacy_repository_test.sql"})
    void when_prompted_must_fail_to_query_a_theoretical_step_3() {
        assertDoesNotThrow(() -> {
                    final Optional<SpecificExecutionStepCandidacyDto> optional = this.theoricalTestStepCandidacyRepository.findQuestions(
                            this.candidacyIdentifier,
                            this.candidateIdentifier,
                            GeneratorIdentifier.forSelectiveProcess(),
                            this.stepIdentifierMultipleChoise
                    );

                    assertNotNull(optional);
                    assertTrue(optional.isEmpty());
                }
        );
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/theorical_test_step_candidacy_repository_test.sql"})
    void when_prompted_must_fail_to_query_a_theoretical_step_4() {
        assertDoesNotThrow(() -> {
                    final Optional<SpecificExecutionStepCandidacyDto> optional = this.theoricalTestStepCandidacyRepository.findQuestions(
                            this.candidacyIdentifier,
                            this.candidateIdentifier,
                            this.selectiveProcessIdentifier,
                            GeneratorIdentifier.forStep()
                    );

                    assertNotNull(optional);
                    assertTrue(optional.isEmpty());
                }
        );
    }

}
package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Step;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteStepCandidacyFindDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
@SpringBootTest
final class StepRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StepRepository stepRepository;

    private List<String> identifiers;
    private String stepIdentifierExternal;
    private String stepIdentifierUploadFile;
    private String selectiveProcessIdentifier;
    private String candidacyIdentifier;
    private String candidateIdentifier;

    @BeforeEach
    void setUp() {
        this.identifiers = List.of(
                "STE-123456789",
                "STE-987654321",
                GeneratorIdentifier.forStep(),
                GeneratorIdentifier.forStep(),
                "STE-564326567"
        );
        this.stepIdentifierExternal = "STE-123456789";
        this.stepIdentifierUploadFile = "STE-987654321";
        this.selectiveProcessIdentifier = "SEL-123456781";
        this.candidacyIdentifier = "APP-123456781";
        this.candidateIdentifier = "CAN-123456781";
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/step_repository_test.sql"})
    void when_requested_you_must_successfully_search_for_steps() {
        assertEquals(3, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "steps"));

        assertDoesNotThrow(() -> {
            final Collection<Step> steps = this.stepRepository.fetchSteps(this.identifiers);

            assertEquals(3, steps.size());
            assertTrue(this.identifiers.containsAll(steps.stream().map(it -> it.getData().getIdentifier()).toList()));
        });

        assertEquals(3, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "steps"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/step_repository_test.sql"})
    void when_prompted_it_must_successfully_fetch() {
        assertDoesNotThrow(() -> {
            final Optional<ExecuteStepCandidacyFindDto> optional = this.stepRepository.find(
                    this.stepIdentifierExternal,
                    this.selectiveProcessIdentifier,
                    this.candidacyIdentifier,
                    this.candidateIdentifier
            );

            assertNotNull(optional);
            assertTrue(optional.isPresent());
        });
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/step_repository_test.sql"})
    void when_prompted_it_must_successfully_fetch_2() {
        assertDoesNotThrow(() -> {
            final Optional<ExecuteStepCandidacyFindDto> optional = this.stepRepository.find(
                    this.stepIdentifierUploadFile,
                    this.selectiveProcessIdentifier,
                    this.candidacyIdentifier,
                    this.candidateIdentifier
            );

            assertNotNull(optional);
            assertTrue(optional.isPresent());
        });
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/step_repository_test.sql"})
    void when_prompted_should_fetch_failed_1() {
        assertDoesNotThrow(() -> {
            final Optional<ExecuteStepCandidacyFindDto> optional = this.stepRepository.find(
                    GeneratorIdentifier.forStep(),
                    this.selectiveProcessIdentifier,
                    this.candidacyIdentifier,
                    this.candidateIdentifier
            );

            assertNotNull(optional);
            assertTrue(optional.isEmpty());
        });
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/step_repository_test.sql"})
    void when_prompted_should_fetch_failed_2() {
        assertDoesNotThrow(() -> {
            final Optional<ExecuteStepCandidacyFindDto> optional = this.stepRepository.find(
                    this.stepIdentifierExternal,
                    GeneratorIdentifier.forSelectiveProcess(),
                    this.candidacyIdentifier,
                    this.candidateIdentifier
            );

            assertNotNull(optional);
            assertTrue(optional.isEmpty());
        });
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/step_repository_test.sql"})
    void when_prompted_should_fetch_failed_3() {
        assertDoesNotThrow(() -> {
            final Optional<ExecuteStepCandidacyFindDto> optional = this.stepRepository.find(
                    this.stepIdentifierExternal,
                    this.selectiveProcessIdentifier,
                    GeneratorIdentifier.forCandidacy(),
                    this.candidateIdentifier
            );

            assertNotNull(optional);
            assertTrue(optional.isEmpty());
        });
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/step_repository_test.sql"})
    void when_prompted_should_fetch_failed_4() {
        assertDoesNotThrow(() -> {
            final Optional<ExecuteStepCandidacyFindDto> optional = this.stepRepository.find(
                    this.stepIdentifierExternal,
                    this.selectiveProcessIdentifier,
                    this.candidacyIdentifier,
                    GeneratorIdentifier.forCandidate()
            );

            assertNotNull(optional);
            assertTrue(optional.isEmpty());
        });
    }

    @ParameterizedTest
    @EnumSource(value = StatusStepCandidacy.class)
    @Transactional
    @Sql(scripts = {"/script/step_repository_test.sql"})
    void when_requested_you_must_update_the_status_of_a_stage_of_a_successful_application(final StatusStepCandidacy status) {
        assertDoesNotThrow(() -> this.stepRepository.updateStatusStepCandidacy(this.stepIdentifierExternal, this.candidacyIdentifier, status));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/step_repository_test.sql"})
    void when_requested_it_must_throw_a_NotFoundException_when_updating_the_status_of_an_application_step() {
        assertThrows(
                NotFoundException.class,
                () -> this.stepRepository.updateStatusStepCandidacy(GeneratorIdentifier.forStep(), this.candidacyIdentifier, StatusStepCandidacy.COMPLETED)
        );
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/step_repository_test.sql"})
    void when_requested_it_must_throw_a_NotFoundException_when_updating_the_status_of_an_application_step_2() {
        assertThrows(
                NotFoundException.class,
                () -> this.stepRepository.updateStatusStepCandidacy(this.stepIdentifierExternal, GeneratorIdentifier.forCandidacy(), StatusStepCandidacy.COMPLETED)
        );
    }

}
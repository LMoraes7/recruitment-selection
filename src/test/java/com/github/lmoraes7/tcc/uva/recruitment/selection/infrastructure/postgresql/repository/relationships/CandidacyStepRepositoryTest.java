package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.FindStepsDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.entity.StepCandidacyEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.batch.SaveCandidacyStepBatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentMatchers;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.query.CandidacyStepCommands.*;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

final class CandidacyStepRepositoryTest {
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final CandidacyStepRepository candidacyStepRepository = new CandidacyStepRepository(this.jdbcTemplate);

    private String candidacyIdentifier;
    private String stepIdentifier;
    private List<StepCandidacyEntity> stepsEntity;
    private List<FindStepsDto> findStepsDtos;

    @BeforeEach
    void setUp() {
        this.candidacyIdentifier = GeneratorIdentifier.forCandidacy();
        this.stepIdentifier = GeneratorIdentifier.forStep();
        this.stepsEntity = List.of(
                dummyObject(StepCandidacyEntity.class),
                dummyObject(StepCandidacyEntity.class),
                dummyObject(StepCandidacyEntity.class),
                dummyObject(StepCandidacyEntity.class)
        );
        this.findStepsDtos = List.of(
                dummyObject(FindStepsDto.class),
                dummyObject(FindStepsDto.class)
        );
    }

    @Test
    void when_requested_you_must_save_an_application_with_its_steps_successfully() {
        when(this.jdbcTemplate.batchUpdate(
                SAVE.sql,
                new SaveCandidacyStepBatch(
                        candidacyIdentifier,
                        stepsEntity
                )
        )).thenReturn(new int[]{0});

        assertDoesNotThrow(() -> this.candidacyStepRepository.save(this.candidacyIdentifier, this.stepsEntity));

        verify(this.jdbcTemplate, only()).batchUpdate(
                SAVE.sql,
                new SaveCandidacyStepBatch(
                        this.candidacyIdentifier,
                        this.stepsEntity
                )
        );
    }

    @Test
    void when_prompted_must_successfully_pursue_steps() {
        when(this.jdbcTemplate.query(
                eq(SELECT_STEPS.sql),
                ArgumentMatchers.<RowMapper<FindStepsDto>>any(),
                eq(this.candidacyIdentifier),
                eq(this.stepIdentifier),
                eq(this.stepIdentifier)
        )).thenReturn(this.findStepsDtos);

        assertDoesNotThrow(() -> this.candidacyStepRepository.getSteps(this.candidacyIdentifier, this.stepIdentifier));

        verify(this.jdbcTemplate, only()).query(
                eq(SELECT_STEPS.sql),
                ArgumentMatchers.<RowMapper<FindStepsDto>>any(),
                eq(this.candidacyIdentifier),
                eq(this.stepIdentifier),
                eq(this.stepIdentifier)
        );
    }

    @ParameterizedTest
    @EnumSource(value = StatusStepCandidacy.class)
    void when_prompted_it_should_update_step_successfully(final StatusStepCandidacy status) {
        when(this.jdbcTemplate.update(
                UPDATE_STATUS.sql,
                status.name(),
                this.candidacyIdentifier,
                this.stepIdentifier
        )).thenReturn(1);

        assertDoesNotThrow(() -> this.candidacyStepRepository.updateStatus(this.candidacyIdentifier, this.stepIdentifier, status));

        verify(this.jdbcTemplate, only()).update(
                UPDATE_STATUS.sql,
                status.name(),
                this.candidacyIdentifier,
                this.stepIdentifier
        );
    }

    @Test
    void when_prompted_must_successfully_fetch_a_step() {
        when(this.jdbcTemplate.queryForObject(
                eq(SELECT_ONE_STEP.sql),
                ArgumentMatchers.<RowMapper<FindStepsDto>>any(),
                eq(this.candidacyIdentifier),
                eq(this.stepIdentifier)
        )).thenReturn(this.findStepsDtos.get(0));

        Optional<FindStepsDto> optionalFindStepsDto =
                assertDoesNotThrow(() -> this.candidacyStepRepository.getStep(this.candidacyIdentifier, this.stepIdentifier));

        assertTrue(optionalFindStepsDto.isPresent());

        verify(this.jdbcTemplate, only()).queryForObject(
                eq(SELECT_ONE_STEP.sql),
                ArgumentMatchers.<RowMapper<FindStepsDto>>any(),
                eq(this.candidacyIdentifier),
                eq(this.stepIdentifier)
        );
    }

    @Test
    void when_prompted_should_fetch_a_failed_step() {
        when(this.jdbcTemplate.queryForObject(
                eq(SELECT_ONE_STEP.sql),
                ArgumentMatchers.<RowMapper<FindStepsDto>>any(),
                eq(this.candidacyIdentifier),
                eq(this.stepIdentifier)
        )).thenThrow(EmptyResultDataAccessException.class);

        Optional<FindStepsDto> optionalFindStepsDto =
                assertDoesNotThrow(() -> this.candidacyStepRepository.getStep(this.candidacyIdentifier, this.stepIdentifier));

        assertTrue(optionalFindStepsDto.isEmpty());

        verify(this.jdbcTemplate, only()).queryForObject(
                eq(SELECT_ONE_STEP.sql),
                ArgumentMatchers.<RowMapper<FindStepsDto>>any(),
                eq(this.candidacyIdentifier),
                eq(this.stepIdentifier)
        );
    }
}
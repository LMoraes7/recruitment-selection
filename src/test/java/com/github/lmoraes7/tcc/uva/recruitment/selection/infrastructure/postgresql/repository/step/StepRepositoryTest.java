package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Step;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteStepCandidacyFindDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.StepFindRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.StepWithIdAndTypeRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo.StepFindVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.FIND;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.SELECT_IDENTIFIERS_IN;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class StepRepositoryTest {
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final StepWithIdAndTypeRowMapper stepWithIdAndTypeRowMapper = mock(StepWithIdAndTypeRowMapper.class);
    private final StepFindRowMapper stepFindRowMapper = mock(StepFindRowMapper.class);
    private final StepRepository stepRepository = new StepRepository(
            this.jdbcTemplate,
            this.stepWithIdAndTypeRowMapper,
            this.stepFindRowMapper
    );

    private Step step;
    private List<String> identifiers;

    private String stepIdentifier;
    private String selectiveProcessIdentifier;
    private String candidacyIdentifier;
    private String candidateIdentifier;
    private StepFindVo stepFindVo;

    @BeforeEach
    void setUp() {
        this.step = dummyObject(ExternalStep.class);
        this.identifiers = List.of(
                GeneratorIdentifier.forStep(),
                GeneratorIdentifier.forStep(),
                GeneratorIdentifier.forStep(),
                GeneratorIdentifier.forStep()
        );
        this.stepIdentifier = GeneratorIdentifier.forStep();
        this.selectiveProcessIdentifier = GeneratorIdentifier.forSelectiveProcess();
        this.candidacyIdentifier = GeneratorIdentifier.forCandidacy();
        this.candidateIdentifier = GeneratorIdentifier.forCandidate();
        this.stepFindVo = new StepFindVo(
                this.stepIdentifier,
                StatusStepCandidacy.BLOCKED,
                7L,
                LocalDate.now()
        );
    }

    @Test
    void when_requested_you_must_successfully_search_for_steps() {
        final String inSql = String.join(",", Collections.nCopies(this.identifiers.size(), "?"));

        when(this.jdbcTemplate.query(
                String.format(SELECT_IDENTIFIERS_IN.sql, inSql),
                this.stepWithIdAndTypeRowMapper,
                this.identifiers.toArray()
        )).thenReturn(List.of(this.step));

        assertDoesNotThrow(() -> this.stepRepository.fetchSteps(this.identifiers));

        verify(this.jdbcTemplate, only()).query(
                String.format(SELECT_IDENTIFIERS_IN.sql, inSql),
                this.stepWithIdAndTypeRowMapper,
                this.identifiers.toArray()
        );
    }

    @Test
    void when_prompted_it_must_successfully_fetch() {
        when(this.jdbcTemplate.queryForObject(
                FIND.sql,
                this.stepFindRowMapper,
                this.stepIdentifier,
                this.selectiveProcessIdentifier,
                this.candidacyIdentifier,
                this.candidateIdentifier
        )).thenReturn(this.stepFindVo);

        assertDoesNotThrow(() -> {
            final Optional<ExecuteStepCandidacyFindDto> optional = this.stepRepository.find(
                    this.stepIdentifier,
                    this.selectiveProcessIdentifier,
                    this.candidacyIdentifier,
                    this.candidateIdentifier
            );

            assertNotNull(optional);
            assertTrue(optional.isPresent());
        });

        verify(this.jdbcTemplate, only()).queryForObject(
                FIND.sql,
                this.stepFindRowMapper,
                this.stepIdentifier,
                this.selectiveProcessIdentifier,
                this.candidacyIdentifier,
                this.candidateIdentifier
        );
    }

    @Test
    void when_prompted_should_fetch_failed() {
        when(this.jdbcTemplate.queryForObject(
                FIND.sql,
                this.stepFindRowMapper,
                this.stepIdentifier,
                this.selectiveProcessIdentifier,
                this.candidacyIdentifier,
                this.candidateIdentifier
        )).thenThrow(EmptyResultDataAccessException.class);

        assertDoesNotThrow(() -> {
            final Optional<ExecuteStepCandidacyFindDto> optional = this.stepRepository.find(
                    this.stepIdentifier,
                    this.selectiveProcessIdentifier,
                    this.candidacyIdentifier,
                    this.candidateIdentifier
            );

            assertNotNull(optional);
            assertTrue(optional.isEmpty());
        });

        verify(this.jdbcTemplate, only()).queryForObject(
                FIND.sql,
                this.stepFindRowMapper,
                this.stepIdentifier,
                this.selectiveProcessIdentifier,
                this.candidacyIdentifier,
                this.candidateIdentifier
        );
    }

}
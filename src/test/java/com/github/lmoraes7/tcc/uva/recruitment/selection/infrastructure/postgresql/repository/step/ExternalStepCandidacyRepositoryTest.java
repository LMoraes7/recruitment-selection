package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.SpecificExecutionStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.ExternalStepCandidacyRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo.ExternalStepCandidacyVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.EXTERNAL_TO_BE_EXECUTED;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.UPDATE_DATA_EXTERNAL;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class ExternalStepCandidacyRepositoryTest {
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final ExternalStepCandidacyRowMapper externalStepCandidacyRowMapper = mock(ExternalStepCandidacyRowMapper.class);
    private final ExternalStepCandidacyRepository externalStepCandidacyRepository = new ExternalStepCandidacyRepository(this.jdbcTemplate, this.externalStepCandidacyRowMapper);

    private String candidacyIdentifier;
    private String candidateIdentifier;
    private String selectiveProcessIdentifier;
    private String stepIdentifier;
    private ExternalStepCandidacyVo externalStepCandidacyVo;

    @BeforeEach
    void setUp() {
        this.candidacyIdentifier = GeneratorIdentifier.forCandidacy();
        this.candidateIdentifier = GeneratorIdentifier.forCandidate();
        this.selectiveProcessIdentifier = GeneratorIdentifier.forSelectiveProcess();
        this.stepIdentifier = GeneratorIdentifier.forStep();
        this.externalStepCandidacyVo = dummyObject(ExternalStepCandidacyVo.class);
    }

    @Test
    void when_requested_must_consult_a_upload_file_step_successfully() {
        when(this.jdbcTemplate.queryForObject(
                EXTERNAL_TO_BE_EXECUTED.sql,
                this.externalStepCandidacyRowMapper,
                this.candidacyIdentifier,
                this.candidateIdentifier,
                this.selectiveProcessIdentifier,
                this.stepIdentifier
        )).thenReturn(this.externalStepCandidacyVo);

        assertDoesNotThrow(() -> {
            final Optional<SpecificExecutionStepCandidacyDto> optional = this.externalStepCandidacyRepository.find(
                    this.candidacyIdentifier,
                    this.candidateIdentifier,
                    this.selectiveProcessIdentifier,
                    this.stepIdentifier
            );

            assertNotNull(optional);
            assertTrue(optional.isPresent());
        });

        verify(this.jdbcTemplate, only()).queryForObject(
                EXTERNAL_TO_BE_EXECUTED.sql,
                this.externalStepCandidacyRowMapper,
                this.candidacyIdentifier,
                this.candidateIdentifier,
                this.selectiveProcessIdentifier,
                this.stepIdentifier
        );
    }

    @Test
    void when_prompted_must_fail_to_query_a_external_step() {
        when(this.jdbcTemplate.queryForObject(
                EXTERNAL_TO_BE_EXECUTED.sql,
                this.externalStepCandidacyRowMapper,
                this.candidacyIdentifier,
                this.candidateIdentifier,
                this.selectiveProcessIdentifier,
                this.stepIdentifier
        )).thenThrow(EmptyResultDataAccessException.class);

        assertDoesNotThrow(() -> {
                    final Optional<SpecificExecutionStepCandidacyDto> optional = this.externalStepCandidacyRepository.find(
                            this.candidacyIdentifier,
                            this.candidateIdentifier,
                            this.selectiveProcessIdentifier,
                            this.stepIdentifier
                    );

                    assertNotNull(optional);
                    assertTrue(optional.isEmpty());
                }
        );

        verify(this.jdbcTemplate, only()).queryForObject(
                EXTERNAL_TO_BE_EXECUTED.sql,
                this.externalStepCandidacyRowMapper,
                this.candidacyIdentifier,
                this.candidateIdentifier,
                this.selectiveProcessIdentifier,
                this.stepIdentifier
        );
    }

    @Test
    void when_prompted_it_should_update_step_successfully() {
        when(this.jdbcTemplate.update(
                UPDATE_DATA_EXTERNAL.sql,
                this.externalStepCandidacyVo.getExternalLink(),
                this.externalStepCandidacyVo.getExternalDateTime(),
                this.candidacyIdentifier,
                this.stepIdentifier
        )).thenReturn(1);

        assertDoesNotThrow(() -> this.externalStepCandidacyRepository.updateData(
                this.candidacyIdentifier,
                this.stepIdentifier,
                this.externalStepCandidacyVo.getExternalLink(),
                this.externalStepCandidacyVo.getExternalDateTime()
        ));

        verify(this.jdbcTemplate, only()).update(
                UPDATE_DATA_EXTERNAL.sql,
                this.externalStepCandidacyVo.getExternalLink(),
                this.externalStepCandidacyVo.getExternalDateTime(),
                this.candidacyIdentifier,
                this.stepIdentifier
        );
    }

}
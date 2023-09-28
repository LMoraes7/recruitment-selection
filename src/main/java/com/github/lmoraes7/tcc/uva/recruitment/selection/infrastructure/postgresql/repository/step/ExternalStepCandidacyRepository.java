package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.SpecificExecutionStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.ExternalStepCandidacyRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo.ExternalStepCandidacyVo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.EXTERNAL_TO_BE_EXECUTED;

@Repository
public class ExternalStepCandidacyRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ExternalStepCandidacyRowMapper externalStepCandidacyRowMapper;

    public ExternalStepCandidacyRepository(
            final JdbcTemplate jdbcTemplate,
            final ExternalStepCandidacyRowMapper externalStepCandidacyRowMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.externalStepCandidacyRowMapper = externalStepCandidacyRowMapper;
    }

    public Optional<SpecificExecutionStepCandidacyDto> find(
            final String candidacyIdentifier,
            final String candidateIdentifier,
            final String selectiveProcessIdentifier,
            final String stepIdentifier
    ) {
        ExternalStepCandidacyVo result = null;
        try {
            result = this.jdbcTemplate.queryForObject(
                    EXTERNAL_TO_BE_EXECUTED.sql,
                    this.externalStepCandidacyRowMapper,
                    candidacyIdentifier,
                    candidateIdentifier,
                    selectiveProcessIdentifier,
                    stepIdentifier
            );
        } catch (final EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }

        return Optional.of(ConverterHelper.externalVotoDto(result));
    }

}

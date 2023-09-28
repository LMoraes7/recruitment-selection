package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.SpecificExecutionStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.UploadFileStepCandidacyRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo.UploadFileStepCandidacyVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.FIND_FILES_TO_BE_SENT;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.FIND_QUESTIONS_TO_BE_EXECUTED;

@Repository
public class UploadFileStepCandidacyRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UploadFileStepCandidacyRowMapper uploadFileStepCandidacyRowMapper;

    public UploadFileStepCandidacyRepository(
            final JdbcTemplate jdbcTemplate,
            final UploadFileStepCandidacyRowMapper uploadFileStepCandidacyRowMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.uploadFileStepCandidacyRowMapper = uploadFileStepCandidacyRowMapper;
    }

    public Optional<SpecificExecutionStepCandidacyDto> findFileToBeSent(
            final String candidacyIdentifier,
            final String candidateIdentifier,
            final String selectiveProcessIdentifier,
            final String stepIdentifier
    ) {
        final List<UploadFileStepCandidacyVo> result = this.jdbcTemplate.query(
                FIND_FILES_TO_BE_SENT.sql,
                this.uploadFileStepCandidacyRowMapper,
                candidacyIdentifier,
                candidateIdentifier,
                selectiveProcessIdentifier,
                stepIdentifier
        );

        if (result.isEmpty())
            return Optional.empty();

        return Optional.of(ConverterHelper.uploadFileVotoDto(result));
    }

}

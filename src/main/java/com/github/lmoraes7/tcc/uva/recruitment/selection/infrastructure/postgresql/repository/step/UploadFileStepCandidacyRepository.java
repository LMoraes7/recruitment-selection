package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeFile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteUploadFileStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ResponsesFromAnExecutedUploadFileStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.SpecificExecutionStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.batch.SaveUploadFileStepCandidacyBatch;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.UploadFileStepCandidacyRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo.UploadFileStepCandidacyVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.*;

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

    public void saveTestExecuted(
            final String candidacyIdentifier,
            final String stepIdentifier,
            final ExecuteUploadFileStepCandidacyDto uploadFile
    ) {
        this.jdbcTemplate.batchUpdate(
                SAVE_EXECUTION_UPLOAD_FILES_STEP.sql,
                new SaveUploadFileStepCandidacyBatch(
                        candidacyIdentifier,
                        stepIdentifier,
                        uploadFile.getFiles()
                )
        );
    }

    public List<ResponsesFromAnExecutedUploadFileStep> consultTestExecuted(
            final String applicationIdentifier,
            final String stepIdentifier
    ) {
        return this.jdbcTemplate.query(
                FIND_FILES_UPLOADS.sql,
                (rs, rowNumber) -> new ResponsesFromAnExecutedUploadFileStep(rs.getString("file_name"), rs.getBytes("file"), TypeFile.valueOf(rs.getString("file_type"))),
                applicationIdentifier,
                stepIdentifier
        );
    }

}

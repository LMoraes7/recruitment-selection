package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.UploadFileStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.batch.SaveUploadFileStepBatch;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity.UploadFileStepEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.SAVE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.SAVE_UPLOAD_DATA;

@Repository
public class UploadFilelStepRepository {
    private final JdbcTemplate jdbcTemplate;

    public UploadFilelStepRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UploadFileStep save(UploadFileStep step) {
        final UploadFileStepEntity entity = ConverterHelper.toEntity(step);

        this.jdbcTemplate.update(
                SAVE.sql,
                entity.getIdentifier(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getType()
        );

        this.jdbcTemplate.batchUpdate(
                SAVE_UPLOAD_DATA.sql,
                new SaveUploadFileStepBatch(
                        entity.getIdentifier(),
                        entity.getFiles().stream().toList()
                )
        );

        return step;
    }

}

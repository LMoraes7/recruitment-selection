package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.UploadFileStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.batch.SaveUploadFileStepBatch;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity.UploadFileStepEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.SAVE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.SAVE_UPLOAD_DATA;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class UploadFilelStepRepositoryTest {
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final UploadFilelStepRepository uploadFilelStepRepository = new UploadFilelStepRepository(this.jdbcTemplate);

    private UploadFileStep step;

    @BeforeEach
    void setUp() {
        this.step = dummyObject(UploadFileStep.class);
    }

    @Test
    void when_prompted_it_should_save_a_step_successfully() {
        final UploadFileStepEntity entity = ConverterHelper.toEntity(step);

        when(this.jdbcTemplate.update(
                SAVE.sql,
                entity.getIdentifier(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getType()
        )).thenReturn(1);
        when(this.jdbcTemplate.batchUpdate(
                SAVE_UPLOAD_DATA.sql,
                new SaveUploadFileStepBatch(
                        entity.getIdentifier(),
                        entity.getFiles().stream().toList()
                )
        )).thenReturn(new int[]{0});

        assertDoesNotThrow(() -> this.uploadFilelStepRepository.save(this.step));

        verify(this.jdbcTemplate, times(1)).update(
                SAVE.sql,
                entity.getIdentifier(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getType()
        );
        verify(this.jdbcTemplate, times(1)).batchUpdate(
                SAVE_UPLOAD_DATA.sql,
                new SaveUploadFileStepBatch(
                        entity.getIdentifier(),
                        entity.getFiles().stream().toList()
                )
        );
        verifyNoMoreInteractions(this.jdbcTemplate);
    }
}
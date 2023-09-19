package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.TheoricalTestStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.batch.SaveTheoricalTestStepBatch;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity.TheoricalTestStepEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.SAVE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.SAVE_THEORICAL_DATA;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class TheoricalTestlStepRepositoryTest {
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final TheoricalTestlStepRepository theoricalTestlStepRepository = new TheoricalTestlStepRepository(this.jdbcTemplate);

    private TheoricalTestStep step;

    @BeforeEach
    void setUp() {
        this.step = dummyObject(TheoricalTestStep.class);
    }

    @Test
    void when_prompted_it_should_save_a_step_successfully() {
        final TheoricalTestStepEntity entity = ConverterHelper.toEntity(this.step);

        when(this.jdbcTemplate.update(
                SAVE.sql,
                entity.getIdentifier(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getType()
        )).thenReturn(1);
        when(this.jdbcTemplate.batchUpdate(
                SAVE_THEORICAL_DATA.sql,
                new SaveTheoricalTestStepBatch(
                        entity.getIdentifier(),
                        entity.getQuestionIdentifiers()
                )
        )).thenReturn(new int[]{0});

        assertDoesNotThrow(() -> this.theoricalTestlStepRepository.save(this.step));

        verify(this.jdbcTemplate, times(1)).update(
                SAVE.sql,
                entity.getIdentifier(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getType()
        );
        verify(this.jdbcTemplate, times(1)).batchUpdate(
                SAVE_THEORICAL_DATA.sql,
                new SaveTheoricalTestStepBatch(
                        entity.getIdentifier(),
                        entity.getQuestionIdentifiers()
                )
        );
        verifyNoMoreInteractions(this.jdbcTemplate);
    }

}
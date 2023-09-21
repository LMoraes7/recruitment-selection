package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.batch.SaveSelectiveProcessStepBatch;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.query.SelectiveProcessCommands;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.entity.SelectiveProcessEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.entity.StepSelectiveProcessEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Set;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class SelectiveProcessStepRepositoryTest {
    private final JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    private final SelectiveProcessStepRepository selectiveProcessStepRepository = new SelectiveProcessStepRepository(this.jdbcTemplate);

    private String selectiveProcessIdentifier;
    private List<StepSelectiveProcessEntity> stepsEntity;

    @BeforeEach
    void setUp() {
        final SelectiveProcessEntity entity = ConverterHelper.toEntity(
                new SelectiveProcess(
                        GeneratorIdentifier.forSelectiveProcess(),
                        dummyObject(String.class),
                        dummyObject(String.class),
                        dummyObject(String.class),
                        dummyObject(StatusSelectiveProcess.class),
                        Set.of(dummyObject(String.class)),
                        Set.of(dummyObject(String.class)),
                        Set.of(dummyObject(String.class)),
                        List.of(dummyObject(ExternalStep.class), dummyObject(ExternalStep.class), dummyObject(ExternalStep.class), dummyObject(ExternalStep.class))
                )
        );

        this.selectiveProcessIdentifier = entity.getIdentifier();
        this.stepsEntity = entity.getSteps();
    }

    @Test
    void when_prompted_it_should_save_successfully() {
        when(this.jdbcTemplate.batchUpdate(
                SelectiveProcessCommands.SAVE.sql,
                new SaveSelectiveProcessStepBatch(
                        this.selectiveProcessIdentifier,
                        this.stepsEntity
                )
        )).thenReturn(new int[]{0});

        assertDoesNotThrow(() -> this.selectiveProcessStepRepository.save(this.selectiveProcessIdentifier, this.stepsEntity));

        verify(this.jdbcTemplate, only()).batchUpdate(
                SelectiveProcessCommands.SAVE.sql,
                new SaveSelectiveProcessStepBatch(
                        this.selectiveProcessIdentifier,
                        this.stepsEntity
                )
        );
    }

}
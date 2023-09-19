package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.SAVE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class ExternalStepRepositoryTest {
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final ExternalStepRepository externalStepRepository = new ExternalStepRepository(this.jdbcTemplate);

    private ExternalStep step;

    @BeforeEach
    void setUp() {
        this.step = dummyObject(ExternalStep.class);
    }

    @Test
    void when_prompted_it_should_save_a_step_successfully() {
        when(this.jdbcTemplate.update(
                SAVE.sql,
                this.step.getData().getIdentifier(),
                this.step.getData().getTitle(),
                this.step.getData().getDescription(),
                this.step.getData().getType().name()
        )).thenReturn(1);

        assertDoesNotThrow(() -> this.externalStepRepository.save(this.step));

        verify(this.jdbcTemplate, only()).update(
                SAVE.sql,
                this.step.getData().getIdentifier(),
                this.step.getData().getTitle(),
                this.step.getData().getDescription(),
                this.step.getData().getType().name()
        );
    }

}
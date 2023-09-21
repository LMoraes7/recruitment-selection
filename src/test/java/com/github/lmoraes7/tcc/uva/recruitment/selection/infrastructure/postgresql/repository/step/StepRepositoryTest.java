package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Step;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.StepWithIdAndTypeRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.List;


import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.SELECT_IDENTIFIERS_IN;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class StepRepositoryTest {
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final StepWithIdAndTypeRowMapper stepWithIdAndTypeRowMapper = mock(StepWithIdAndTypeRowMapper.class);
    private final StepRepository stepRepository = new StepRepository(
            this.jdbcTemplate,
            this.stepWithIdAndTypeRowMapper
    );

    private Step step;
    private List<String> identifiers;

    @BeforeEach
    void setUp() {
        this.step = dummyObject(ExternalStep.class);
        this.identifiers = List.of(
                GeneratorIdentifier.forStep(),
                GeneratorIdentifier.forStep(),
                GeneratorIdentifier.forStep(),
                GeneratorIdentifier.forStep()
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
}
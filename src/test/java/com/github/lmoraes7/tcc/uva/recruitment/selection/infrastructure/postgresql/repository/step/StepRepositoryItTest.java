package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
@SpringBootTest
final class StepRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StepRepository stepRepository;

    private List<String> identifiers;

    @BeforeEach
    void setUp() {
        this.identifiers = List.of(
                "STE-123456789",
                "STE-987654321",
                GeneratorIdentifier.forStep(),
                GeneratorIdentifier.forStep(),
                "STE-564326567"
        );
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/step_repository_test.sql"})
    void when_requested_you_must_successfully_search_for_steps() {
        assertEquals(3, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "steps"));

        assertDoesNotThrow(() -> {
            final Collection<Step> steps = this.stepRepository.fetchSteps(this.identifiers);

            assertEquals(3, steps.size());
            assertTrue(this.identifiers.containsAll(steps.stream().map(it -> it.getData().getIdentifier()).toList()));
        });

        assertEquals(3, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "steps"));
    }

}
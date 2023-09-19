package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("integration")
@SpringBootTest
final class ExternalStepRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ExternalStepRepository externalStepRepository;

    private ExternalStep step;

    @BeforeEach
    void setUp() {
        this.step = new ExternalStep(
                new StepData(
                        GeneratorIdentifier.forStep(),
                        "title",
                        "description",
                        TypeStep.EXTERNAL
                )
        );
    }

    @Test
    @Transactional
    void when_prompted_it_should_save_a_step_successfully() {
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "steps"));

        assertDoesNotThrow(() -> this.externalStepRepository.save(this.step));

        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "steps"));
    }

}
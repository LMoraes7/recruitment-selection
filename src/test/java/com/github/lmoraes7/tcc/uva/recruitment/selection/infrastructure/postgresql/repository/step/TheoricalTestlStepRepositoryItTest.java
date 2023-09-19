package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.TheoricalTestStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("integration")
@SpringBootTest
final class TheoricalTestlStepRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TheoricalTestlStepRepository theoricalTestlStepRepository;

    private TheoricalTestStep step;

    @BeforeEach
    void setUp() {
        this.step = new TheoricalTestStep(
                new StepData(
                        GeneratorIdentifier.forStep(),
                        "title",
                        "description",
                        TypeStep.THEORETICAL_TEST
                ),
                List.of(
                        new Question("QUE-123456789"),
                        new Question("QUE-987654321")
                )
        );
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/theorical_test_step_repository_test.sql"})
    void when_prompted_it_should_save_a_step_successfully() {
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "steps"));
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "steps_theoretical_tests"));

        assertDoesNotThrow(() -> this.theoricalTestlStepRepository.save(this.step));

        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "steps"));
        assertEquals(this.step.getQuestions().size(), JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "steps_theoretical_tests"));
    }

}
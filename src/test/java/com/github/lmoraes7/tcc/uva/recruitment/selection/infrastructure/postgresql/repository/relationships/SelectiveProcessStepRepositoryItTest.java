package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.entity.SelectiveProcessEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.entity.StepSelectiveProcessEntity;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("integration")
@SpringBootTest
final class SelectiveProcessStepRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SelectiveProcessStepRepository selectiveProcessStepRepository;

    private String selectiveProcessIdentifier;
    private List<StepSelectiveProcessEntity> stepsEntity;

    @BeforeEach
    void setUp() {
        final SelectiveProcessEntity entity = ConverterHelper.toEntity(
                new SelectiveProcess(
                        "SEL-123456789",
                        "Title",
                        "Description",
                        "Desired Position",
                        StatusSelectiveProcess.IN_PROGRESS,
                        Set.of("responsibilities 1", "responsibilities 2", "responsibilities 3"),
                        Set.of("requirements 1", "requirements 2", "requirements 3"),
                        Set.of("additional Infos 1", "additional Infos 2", "additional Infos 3"),
                        List.of(
                                new ExternalStep(
                                        new StepData("STE-123456789"),
                                        null
                                ),
                                new ExternalStep(
                                        new StepData("STE-987654321"),
                                        445675678L
                                ),
                                new ExternalStep(
                                        new StepData("STE-564326567"),
                                        8934756L
                                )
                        )
                )
        );

        this.selectiveProcessIdentifier = entity.getIdentifier();
        this.stepsEntity = entity.getSteps();
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/selective_process_step_repository_test.sql"})
    void when_prompted_it_should_save_successfully() {
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes_steps"));

        assertDoesNotThrow(() -> this.selectiveProcessStepRepository.save(this.selectiveProcessIdentifier, this.stepsEntity));

        assertEquals(stepsEntity.size(), JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes_steps"));
    }

}
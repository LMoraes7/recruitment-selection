package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.entity.StepCandidacyEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("integration")
@SpringBootTest
final class CandidacyStepRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CandidacyStepRepository candidacyStepRepository;

    private String candidacyIdentifier;
    private List<StepCandidacyEntity> stepsEntity;

    @BeforeEach
    void setUp() {
        this.candidacyIdentifier = "APP-123456789";
        this.stepsEntity = List.of(
                new StepCandidacyEntity(
                        "STE-987654321",
                        4564576L,
                        "STE-564326562",
                        StatusStepCandidacy.WAITING_FOR_EXECUTION.name(),
                        LocalDate.now()
                ),
                new StepCandidacyEntity(
                        "STE-564326562",
                        798789L,
                        "STE-564326563",
                        StatusStepCandidacy.BLOCKED.name(),
                        null
                ),
                new StepCandidacyEntity(
                        "STE-564326563",
                        7876576L,
                        "STE-987654324",
                        StatusStepCandidacy.BLOCKED.name(),
                        null
                ),
                new StepCandidacyEntity(
                        "STE-987654324",
                        7876576L,
                        "STE-564326567",
                        StatusStepCandidacy.BLOCKED.name(),
                        null
                ),
                new StepCandidacyEntity(
                        "STE-564326567",
                        7687L,
                        "STE-123456788",
                        StatusStepCandidacy.BLOCKED.name(),
                        null
                ),
                new StepCandidacyEntity(
                        "STE-123456788",
                        null,
                        "STE-123456789",
                        StatusStepCandidacy.BLOCKED.name(),
                        null
                ),
                new StepCandidacyEntity(
                        "STE-123456789",
                        null,
                        null,
                        StatusStepCandidacy.BLOCKED.name(),
                        null
                )
        );
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/candidacy_step_repository_test.sql"})
    void when_requested_you_must_save_an_application_with_its_steps_successfully() {
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications_steps"));

        assertDoesNotThrow(() -> this.candidacyStepRepository.save(this.candidacyIdentifier, this.stepsEntity));

        assertEquals(7, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications_steps"));
    }
}
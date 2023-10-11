package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.FindStepsDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.entity.StepCandidacyEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(3, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications_steps"));

        assertDoesNotThrow(() -> this.candidacyStepRepository.save(this.candidacyIdentifier, this.stepsEntity));

        assertEquals(10, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications_steps"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/candidacy_step_repository_test.sql"})
    void when_prompted_must_successfully_pursue_steps() {
        List<FindStepsDto> findStepsDtos = assertDoesNotThrow(
                () -> this.candidacyStepRepository.getSteps(this.candidacyIdentifier, "STE-987654321")
        );

        assertEquals(findStepsDtos.size(), 2);
        assertEquals(findStepsDtos.get(0).getNextStepIdentifier(), "STE-987654321");
        assertEquals(findStepsDtos.get(1).getStepIdentifier(), "STE-987654321");
    }

    @ParameterizedTest
    @EnumSource(value = StatusStepCandidacy.class)
    @Transactional
    @Sql(scripts = {"/script/candidacy_step_repository_test.sql"})
    void when_prompted_it_should_update_step_successfully(final StatusStepCandidacy status) {
        assertDoesNotThrow(() -> this.candidacyStepRepository.updateStatus(this.candidacyIdentifier, "STE-987654321", status));

        assertTrue(
                JdbcTestUtils.countRowsInTableWhere(
                        this.jdbcTemplate,
                        "applications_steps",
                        "status = '" + status.name() + "'"
                ) >= 1
        );
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/candidacy_step_repository_test.sql"})
    void when_prompted_must_successfully_fetch_a_step() {
        Optional<FindStepsDto> optionalFindStepsDto =
                assertDoesNotThrow(() -> this.candidacyStepRepository.getStep(this.candidacyIdentifier, "STE-987654321"));

        assertTrue(optionalFindStepsDto.isPresent());
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/candidacy_step_repository_test.sql"})
    void when_prompted_should_fetch_a_failed_step() {
        Optional<FindStepsDto> optionalFindStepsDto =
                assertDoesNotThrow(() -> this.candidacyStepRepository.getStep(this.candidacyIdentifier, GeneratorIdentifier.forStep()));

        assertTrue(optionalFindStepsDto.isEmpty());
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/candidacy_step_repository_test.sql"})
    void when_prompted_should_fetch_a_failed_step_2() {
        Optional<FindStepsDto> optionalFindStepsDto =
                assertDoesNotThrow(() -> this.candidacyStepRepository.getStep(GeneratorIdentifier.forCandidacy(), "STE-987654321"));

        assertTrue(optionalFindStepsDto.isEmpty());
    }

}
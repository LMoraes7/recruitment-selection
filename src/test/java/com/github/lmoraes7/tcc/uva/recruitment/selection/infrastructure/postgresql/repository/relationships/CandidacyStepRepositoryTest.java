package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.entity.StepCandidacyEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.batch.SaveCandidacyStepBatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.query.CandidacyStepCommands.SAVE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class CandidacyStepRepositoryTest {
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final CandidacyStepRepository candidacyStepRepository = new CandidacyStepRepository(this.jdbcTemplate);

    private String candidacyIdentifier;
    private List<StepCandidacyEntity> stepsEntity;

    @BeforeEach
    void setUp() {
        this.candidacyIdentifier = GeneratorIdentifier.forCandidacy();
        this.stepsEntity = List.of(
                dummyObject(StepCandidacyEntity.class),
                dummyObject(StepCandidacyEntity.class),
                dummyObject(StepCandidacyEntity.class),
                dummyObject(StepCandidacyEntity.class)
        );
    }

    @Test
    void when_requested_you_must_save_an_application_with_its_steps_successfully() {
        when(this.jdbcTemplate.batchUpdate(
                SAVE.sql,
                new SaveCandidacyStepBatch(
                        candidacyIdentifier,
                        stepsEntity
                )
        )).thenReturn(new int[]{0});

        assertDoesNotThrow(() -> this.candidacyStepRepository.save(this.candidacyIdentifier, this.stepsEntity));

        verify(this.jdbcTemplate, only()).batchUpdate(
                SAVE.sql,
                new SaveCandidacyStepBatch(
                        this.candidacyIdentifier,
                        this.stepsEntity
                )
        );
    }

}
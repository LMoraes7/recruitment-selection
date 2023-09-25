package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.entity.CandidacyEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.CandidacyStepRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.conveter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.query.CandidacyCommands.SAVE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class CandidacyRepositoryTest {
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final CandidacyStepRepository candidacyStepRepository = mock(CandidacyStepRepository.class);
    private final CandidacyRepository candidacyRepository = new CandidacyRepository(this.jdbcTemplate, this.candidacyStepRepository);

    private String candidateIdentifier;
    private String selectiveProcessIdentifier;
    private Candidacy candidacy;

    @BeforeEach
    void setUp() {
        this.candidateIdentifier = GeneratorIdentifier.forCandidate();
        this.selectiveProcessIdentifier = GeneratorIdentifier.forSelectiveProcess();
        this.candidacy = new Candidacy(
                GeneratorIdentifier.forCandidacy(),
                StatusCandidacy.IN_PROGRESS,
                List.of(
                        new ExternalStep(
                                new StepData(GeneratorIdentifier.forStep()),
                                547657L,
                                StatusStepCandidacy.WAITING_FOR_EXECUTION,
                                LocalDate.now()
                        ),
                        new ExternalStep(
                                new StepData(GeneratorIdentifier.forStep()),
                                5476765L,
                                StatusStepCandidacy.BLOCKED
                        ),
                        new ExternalStep(
                                new StepData(GeneratorIdentifier.forStep()),
                                null,
                                StatusStepCandidacy.BLOCKED
                        )
                )
        );
    }

    @Test
    void when_prompted_you_must_save_a_successful_application() {
        final CandidacyEntity entity = toEntity(
                this.candidateIdentifier,
                this.selectiveProcessIdentifier,
                this.candidacy
        );

        when(this.jdbcTemplate.update(
                SAVE.sql,
                entity.getIdentifier(),
                entity.getStatus(),
                entity.getCandidateIdentifier(),
                entity.getSelectiveProcessIdentifier()
        )).thenReturn(1);

        assertDoesNotThrow(
                () -> this.candidacyRepository.save(
                        this.candidateIdentifier,
                        this.selectiveProcessIdentifier,
                        this.candidacy
                )
        );

        verify(this.jdbcTemplate, only()).update(
                SAVE.sql,
                entity.getIdentifier(),
                entity.getStatus(),
                entity.getCandidateIdentifier(),
                entity.getSelectiveProcessIdentifier()
        );
        verify(this.candidacyStepRepository, only()).save(entity.getIdentifier(), entity.getSteps());
    }

}
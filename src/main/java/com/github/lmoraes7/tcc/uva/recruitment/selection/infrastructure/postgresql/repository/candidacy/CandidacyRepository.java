package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.entity.CandidacyEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.CandidacyStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.conveter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.query.CandidacyCommands.SAVE;

@Repository
public class CandidacyRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CandidacyStepRepository candidacyStepRepository;

    @Autowired
    public CandidacyRepository(final JdbcTemplate jdbcTemplate, final CandidacyStepRepository candidacyStepRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.candidacyStepRepository = candidacyStepRepository;
    }

    public Candidacy save(
            final String candidateIdentifier,
            final String selectiveProcessIdentifier,
            final Candidacy candidacy
    ) {
        final CandidacyEntity entity = toEntity(candidateIdentifier, selectiveProcessIdentifier, candidacy);

        this.jdbcTemplate.update(
                SAVE.sql,
                entity.getIdentifier(),
                entity.getStatus(),
                entity.getCandidateIdentifier(),
                entity.getSelectiveProcessIdentifier()
        );

        this.candidacyStepRepository.save(entity.getIdentifier(), entity.getSteps());
        return candidacy;
    }

}

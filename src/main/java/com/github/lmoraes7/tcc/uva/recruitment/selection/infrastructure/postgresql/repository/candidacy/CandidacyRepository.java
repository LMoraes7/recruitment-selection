package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.SpecificCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.entity.CandidacyEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.rowmapper.CandidacyRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.rowmapper.vo.CandidacyVo;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.CandidacyStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.conveter.ConverterHelper.toDto;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.conveter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.query.CandidacyCommands.FIND_BY_ID;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.query.CandidacyCommands.SAVE;

@Repository
public class CandidacyRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CandidacyStepRepository candidacyStepRepository;
    private final CandidacyRowMapper candidacyRowMapper;

    @Autowired
    public CandidacyRepository(
            final JdbcTemplate jdbcTemplate,
            final CandidacyStepRepository candidacyStepRepository,
            final CandidacyRowMapper candidacyRowMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.candidacyStepRepository = candidacyStepRepository;
        this.candidacyRowMapper = candidacyRowMapper;
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

    public Optional<SpecificCandidacyDto> findById(
            final String candidateIdentifier,
            final String candidacyIdentifier
    ) {
        final List<CandidacyVo> result = this.jdbcTemplate.query(
                FIND_BY_ID.sql,
                this.candidacyRowMapper,
                candidacyIdentifier,
                candidateIdentifier
        );

        if (result.isEmpty())
            return Optional.empty();

        return Optional.of(toDto(result));
    }

}

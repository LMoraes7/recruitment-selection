package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.entity.CandidateEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.CandidateProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.converter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.query.CandidateCommands.SAVE;

@Repository
public class CandidateRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CandidateProfileRepository candidateProfileRepository;

    @Autowired
    public CandidateRepository(
            final JdbcTemplate jdbcTemplate,
            final CandidateProfileRepository candidateProfileRepository
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.candidateProfileRepository = candidateProfileRepository;
    }

    public Candidate save(final Candidate candidate) {
        final CandidateEntity candidateEntity = toEntity(candidate);

        this.jdbcTemplate.update(
                SAVE.sql,
                candidateEntity.getIdentifier(),
                candidateEntity.getPersonalData().getName(),
                candidateEntity.getPersonalData().getCpf(),
                candidateEntity.getPersonalData().getEmail(),
                candidateEntity.getPersonalData().getDateOfBirth(),
                candidateEntity.getPersonalData().getPhone(),
                candidateEntity.getPersonalData().getAddress(),
                candidateEntity.getAccessCredentials().getUsername(),
                candidateEntity.getAccessCredentials().getPassword()
        );
        this.candidateProfileRepository.saveRelationship(candidate);
        return candidate;
    }

}

package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.batch.SaveCandidateProfileBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.query.CandidateProfileCommands.SAVE;

@Repository
public class CandidateProfileRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CandidateProfileRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveRelationship(final Candidate candidate) {
        this.jdbcTemplate.batchUpdate(
                SAVE.sql,
                new SaveCandidateProfileBatch(
                        candidate.getIdentifier(),
                        candidate.getAccessCredentials().getProfiles().stream().map(Profile::getIdentifier).toList()
                )
        );
    }

}

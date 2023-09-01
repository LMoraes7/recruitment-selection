package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.batch.SaveCandidateProfileBatch;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.query.CandidateProfileCommands.SAVE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class CandidateProfileRepositoryTest {

    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final CandidateProfileRepository candidateProfileRepository = new CandidateProfileRepository(
            this.jdbcTemplate
    );

    private Candidate candidate;

    @BeforeEach
    void setUp() {
        this.candidate = TestUtils.dummyObject(Candidate.class);
    }

    @Test
    void when_prompted_must_successfully_save_a_candidate_with_their_profiles() {
        when(this.jdbcTemplate.batchUpdate(
                SAVE.sql,
                new SaveCandidateProfileBatch(
                        candidate.getIdentifier(),
                        candidate.getAccessCredentials().getProfiles().stream().map(Profile::getIdentifier).toList()
                )
        )).thenReturn(new int[] {});

        assertDoesNotThrow(() -> this.candidateProfileRepository.saveRelationship(this.candidate));

        verify(this.jdbcTemplate, only()).batchUpdate(
                SAVE.sql,
                new SaveCandidateProfileBatch(
                        candidate.getIdentifier(),
                        candidate.getAccessCredentials().getProfiles().stream().map(Profile::getIdentifier).toList()
                )
        );
    }

}
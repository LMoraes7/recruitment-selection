package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestItUtils.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("integration")
@SpringBootTest
final class CandidateProfileRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CandidateProfileRepository candidateProfileRepository;

    private Candidate candidate;

    @BeforeEach
    void setUp() {
        this.candidate = generateCandidate();
    }

    @Test
    @Transactional
    void when_prompted_must_successfully_save_a_candidate_with_their_profiles() {
        saveProfiles(this.jdbcTemplate, this.candidate.getAccessCredentials().getProfiles());
        saveCandidate(this.jdbcTemplate, this.candidate);

        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "candidates_profiles"));

        assertDoesNotThrow(() -> this.candidateProfileRepository.saveRelationship(this.candidate));

        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "candidates_profiles"));
    }

}
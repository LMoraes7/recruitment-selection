package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.entity.CandidateEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.CandidateProfileRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.converter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.query.CandidateCommands.SAVE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class CandidateRepositoryTest {
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final CandidateProfileRepository candidateProfileRepository = mock(CandidateProfileRepository.class);
    private final CandidateRepository candidateRepository = new CandidateRepository(
            this.jdbcTemplate,
            this.candidateProfileRepository
    );

    private Candidate candidate;

    @BeforeEach
    void setUp() {
        this.candidate = TestUtils.dummyObject(Candidate.class);
    }

    @Test
    void when_prompted_must_successfully_save_candidate() {
        final CandidateEntity candidateEntity = toEntity(this.candidate);

        when(this.jdbcTemplate.update(
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
        )).thenReturn(1);

        assertDoesNotThrow(() -> this.candidateRepository.save(this.candidate));

        verify(this.jdbcTemplate, only()).update(
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
        verify(this.candidateProfileRepository, only()).saveRelationship(this.candidate);
    }

}
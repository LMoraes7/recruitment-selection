package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.entity.CandidateEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.CandidateProfileRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.converter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.query.CandidateCommands.CHANGE_PASSWORD_BY_USERNAME;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.query.CandidateCommands.SAVE;
import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void when_prompted_should_update_password_successfully() {
        when(this.jdbcTemplate.update(
                CHANGE_PASSWORD_BY_USERNAME.sql,
                this.candidate.getAccessCredentials().getPassword(),
                this.candidate.getAccessCredentials().getUsername()
        )).thenReturn(1);

        assertDoesNotThrow(() -> this.candidateRepository.changePassword(
                this.candidate.getAccessCredentials().getUsername(),
                this.candidate.getAccessCredentials().getPassword()
        ));

        verify(this.jdbcTemplate, only()).update(
                CHANGE_PASSWORD_BY_USERNAME.sql,
                this.candidate.getAccessCredentials().getPassword(),
                this.candidate.getAccessCredentials().getUsername()
        );
        verifyNoInteractions(this.candidateProfileRepository);
    }

    @Test
    void when_prompted_should_update_password_failed() {
        when(this.jdbcTemplate.update(
                CHANGE_PASSWORD_BY_USERNAME.sql,
                this.candidate.getAccessCredentials().getPassword(),
                this.candidate.getAccessCredentials().getUsername()
        )).thenReturn(0);

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> this.candidateRepository.changePassword(
                        this.candidate.getAccessCredentials().getUsername(),
                        this.candidate.getAccessCredentials().getPassword()
                ));

        assertNotNull(exception);
        assertEquals(
                this.candidate.getAccessCredentials().getUsername(),
                exception.getCode()
        );
        assertEquals(
                Candidate.class,
                exception.getClassType()
        );

        verify(this.jdbcTemplate, only()).update(
                CHANGE_PASSWORD_BY_USERNAME.sql,
                this.candidate.getAccessCredentials().getPassword(),
                this.candidate.getAccessCredentials().getUsername()
        );
        verifyNoInteractions(this.candidateProfileRepository);
    }

}
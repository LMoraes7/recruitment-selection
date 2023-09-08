package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Function;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.Functionality;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.AccessCredentials;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.Address;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.PersonalData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.Phone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
@SpringBootTest
final class CandidateRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CandidateRepository candidateRepository;

    private Candidate candidate;

    @BeforeEach
    void setUp() {
        this.candidate = new Candidate(
                GeneratorIdentifier.forCandidate(),
                new PersonalData(
                        "Fernando Costa da Silva",
                        "21314335065",
                        "email@email.com.br",
                        LocalDate.now(),
                        new Phone(
                                "21",
                                "947884078"
                        ),
                        new Address(
                                "Avenida Atlântica",
                                570,
                                "APT 304",
                                "Copacabana",
                                "Rio de Janeiro",
                                "RJ",
                                "10856894"
                        )
                ),
                new AccessCredentials(
                        "email@email.com",
                        "hhKJ6hui3%8u&2$jgJhjbJugj",
                        Set.of(
                                new Profile(
                                        "PRO-123456789",
                                        "PROFILE_TEST",
                                        Set.of(
                                                new Function(
                                                        "FUN-123456789",
                                                        Functionality.FUNC_CREATE_PROFILE
                                                ),
                                                new Function(
                                                        "FUN-987654321",
                                                        Functionality.FUNC_CREATE_EMPLOYEE
                                                )
                                        )
                                )
                        )
                )
        );
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/candidate_repository_test.sql"})
    void when_prompted_must_successfully_save_candidate() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "candidates"));
        assertDoesNotThrow(() -> this.candidateRepository.save(this.candidate));
        assertEquals(2, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "candidates"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/candidate_repository_test.sql"})
    void when_prompted_should_update_password_successfully() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "candidates"));
        assertDoesNotThrow(() -> this.candidateRepository.changePassword(
                "email_test@email.com.br",
                this.candidate.getAccessCredentials().getPassword()
        ));
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "candidates"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/candidate_repository_test.sql"})
    void when_prompted_should_update_password_failed() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "candidates"));
        assertThrows(
                NotFoundException.class,
                () -> this.candidateRepository.changePassword(
                        this.candidate.getAccessCredentials().getUsername(),
                        this.candidate.getAccessCredentials().getPassword()
                )
        );
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "candidates"));
    }

}
package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorResetPasswordCode;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Function;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.PasswordChangeRequest;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
@SpringBootTest
final class CommonRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CommonRepository commonRepository;

    private Employee employee;
    private String code;

    @BeforeEach
    void setUp() {
        this.employee = new Employee(
                GeneratorIdentifier.forEmployee(),
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

        this.code = GeneratorResetPasswordCode.execute();
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/commom_repository_test.sql"})
    void when_save_a_record_it_must_save_successfully() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "registered_emails_documents"));
        assertDoesNotThrow(
                () -> this.commonRepository.saveRecords(
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString().substring(0, 11)
                )
        );
        assertEquals(2, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "registered_emails_documents"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/commom_repository_test.sql"})
    void when_saving_a_record_with_an_existing_email_it_should_throw_a_DataIntegrityViolationException() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "registered_emails_documents"));
        assertThrows(
                DataIntegrityViolationException.class,
                () -> this.commonRepository.saveRecords(
                        "email_test@email.com.br",
                        UUID.randomUUID().toString().substring(0, 11)
                )
        );
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "registered_emails_documents"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/commom_repository_test.sql"})
    void when_saving_a_record_with_an_existing_cpf_it_should_throw_a_DataIntegrityViolationException() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "registered_emails_documents"));
        assertThrows(
                DataIntegrityViolationException.class,
                () -> this.commonRepository.saveRecords(
                        UUID.randomUUID().toString(),
                        "27005990048"
                )
        );
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "registered_emails_documents"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/commom_repository_test.sql"})
    void when_prompted_should_save_password_reset_request_successfully() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "password_change_requests"));
        assertDoesNotThrow(() -> this.commonRepository.savePasswordChangeRequest(
                this.employee,
                this.code
        ));
        assertEquals(2, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "password_change_requests"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/commom_repository_test.sql"})
    void when_prompted_should_fetch_a_successful_password_reset_request() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "password_change_requests"));
        assertDoesNotThrow(() -> {
            final Optional<PasswordChangeRequest> optional = this.commonRepository.findPasswordChangeRequestByCode("RST-123456789");

            assertNotNull(optional);
            assertTrue(optional.isPresent());
        });
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "password_change_requests"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/commom_repository_test.sql"})
    void when_prompted_should_fetch_a_failed_password_reset_request() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "password_change_requests"));
        assertDoesNotThrow(() -> {
            final Optional<PasswordChangeRequest> optional = this.commonRepository.findPasswordChangeRequestByCode(this.code);

            assertNotNull(optional);
            assertTrue(optional.isEmpty());
        });
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "password_change_requests"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/commom_repository_test.sql"})
    void when_prompted_must_update_the_use_of_the_password_reset_request() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "password_change_requests"));
        assertDoesNotThrow(() -> this.commonRepository.closePasswordChangeRequest("RST-123456789"));
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "password_change_requests"));
    }

}
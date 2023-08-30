package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorResetPasswordCode;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
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
        this.employee = dummyObject(Employee.class);
        this.code = GeneratorResetPasswordCode.execute();
    }

    @Test
    @Transactional
    void when_save_a_record_it_must_save_successfully() {
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "registered_emails_documents"));

        assertDoesNotThrow(
                () -> this.commonRepository.saveRecords(
                        this.employee.getPersonalData().getEmail(),
                        this.employee.getPersonalData().getCpf()
                )
        );

        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "registered_emails_documents"));
    }

    @Test
    @Transactional
    void when_saving_a_record_with_an_existing_email_it_should_throw_a_DataIntegrityViolationException() {
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "registered_emails_documents"));
        this.commonRepository.saveRecords(
                this.employee.getPersonalData().getEmail(),
                this.employee.getPersonalData().getCpf()
        );
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "registered_emails_documents"));

        assertThrows(
                DataIntegrityViolationException.class,
                () -> this.commonRepository.saveRecords(
                        this.employee.getPersonalData().getEmail(),
                        UUID.randomUUID().toString().substring(0, 11)
                )
        );
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "registered_emails_documents"));
    }

    @Test
    @Transactional
    void when_saving_a_record_with_an_existing_cpf_it_should_throw_a_DataIntegrityViolationException() {
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "registered_emails_documents"));
        this.commonRepository.saveRecords(
                this.employee.getPersonalData().getEmail(),
                this.employee.getPersonalData().getCpf()
        );
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "registered_emails_documents"));

        assertThrows(
                DataIntegrityViolationException.class,
                () -> this.commonRepository.saveRecords(
                        UUID.randomUUID().toString().substring(0, 11),
                        this.employee.getPersonalData().getCpf()
                )
        );
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "registered_emails_documents"));
    }

    @Test
    @Transactional
    void when_prompted_should_save_password_reset_request_successfully() {
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "password_change_requests"));

        assertDoesNotThrow(() -> this.commonRepository.savePasswordChangeRequest(
                this.employee,
                this.code
        ));

        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "password_change_requests"));
    }

}
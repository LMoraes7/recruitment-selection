package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
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
class EmployeeRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

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
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/employee_repository_test.sql"})
    void when_prompted_should_save_an_employee_successfully() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "employees"));
        assertDoesNotThrow(() -> this.employeeRepository.save(this.employee));
        assertEquals(2, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "employees"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/employee_repository_test.sql"})
    void when_prompted_should_update_password_successfully() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "employees"));
        assertDoesNotThrow(() -> this.employeeRepository.changePassword(
                "email_test@email.com.br",
                this.employee.getAccessCredentials().getPassword()
        ));
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "employees"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/employee_repository_test.sql"})
    void when_prompted_should_update_password_failed() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "employees"));
        assertThrows(
                NotFoundException.class,
                () -> this.employeeRepository.changePassword(
                        this.employee.getAccessCredentials().getUsername(),
                        this.employee.getAccessCredentials().getPassword()
                )
        );
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "employees"));
    }

}
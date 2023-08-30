package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestItUtils.generateEmployee;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestItUtils.saveProfileAndFunctions;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("integration")
@SpringBootTest
class EmployeeRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        this.employee = generateEmployee();
    }

    @Test
    @Transactional
    void when_prompted_should_save_an_employee_successfully() {
        saveProfileAndFunctions(this.jdbcTemplate, this.employee.getAccessCredentials().getProfiles().stream().findFirst().get());

        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "employees"));
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "employees_profiles"));

        assertDoesNotThrow(() -> employeeRepository.save(this.employee));

        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "employees"));
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "employees_profiles"));
    }

}
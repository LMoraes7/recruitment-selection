package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestItUtils.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("integration")
@SpringBootTest
final class EmployeeProfileRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EmployeeProfileRepository employeeProfileRepository;

    private Profile profile;
    private Employee employee;

    @BeforeEach
    void setUp() {
        this.profile = generateProfileWithoutFunctions();
        this.employee = generateEmployee(Set.of(this.profile));
    }

    @Test
    @Transactional
    void when_prompted_should_successfully_save_the_relationship() {
        saveProfiles(this.jdbcTemplate, List.of(this.profile));
        saveEmployee(this.jdbcTemplate, this.employee);

        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "employees_profiles"));
        assertDoesNotThrow(() -> this.employeeProfileRepository.saveRelationship(this.employee));
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "employees_profiles"));
    }

}
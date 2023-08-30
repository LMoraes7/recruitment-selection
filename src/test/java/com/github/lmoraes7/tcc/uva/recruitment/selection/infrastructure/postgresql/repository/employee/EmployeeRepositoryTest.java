package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.entity.EmployeeEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.EmployeeProfileRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.converter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.query.EmployeeCommands.SAVE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class EmployeeRepositoryTest {

    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final EmployeeProfileRepository employeeProfileRepository = mock(EmployeeProfileRepository.class);
    private final EmployeeRepository employeeRepository = new EmployeeRepository(this.jdbcTemplate, this.employeeProfileRepository);

    private Employee employee;
    private EmployeeEntity employeeEntity;

    @BeforeEach
    void setUp() {
        this.employee = dummyObject(Employee.class);
        this.employeeEntity = toEntity(this.employee);
    }

    @Test
    void when_prompted_should_save_an_employee_successfully() {
        when(this.jdbcTemplate.update(
                SAVE.sql,
                this.employeeEntity.getIdentifier(),
                this.employeeEntity.getPersonalData().getName(),
                this.employeeEntity.getPersonalData().getCpf(),
                this.employeeEntity.getPersonalData().getEmail(),
                this.employeeEntity.getPersonalData().getDateOfBirth(),
                this.employeeEntity.getPersonalData().getPhone(),
                this.employeeEntity.getPersonalData().getAddress(),
                this.employeeEntity.getAccessCredentials().getUsername(),
                this.employeeEntity.getAccessCredentials().getPassword()
        )).thenReturn(1);

        assertDoesNotThrow(() -> {
            final Employee employeeCreated = this.employeeRepository.save(this.employee);

            assertNotNull(employeeCreated);
            assertEquals(this.employee, employeeCreated);
        });

        verify(this.jdbcTemplate, only()).update(
                SAVE.sql,
                this.employeeEntity.getIdentifier(),
                this.employeeEntity.getPersonalData().getName(),
                this.employeeEntity.getPersonalData().getCpf(),
                this.employeeEntity.getPersonalData().getEmail(),
                this.employeeEntity.getPersonalData().getDateOfBirth(),
                this.employeeEntity.getPersonalData().getPhone(),
                this.employeeEntity.getPersonalData().getAddress(),
                this.employeeEntity.getAccessCredentials().getUsername(),
                this.employeeEntity.getAccessCredentials().getPassword()
        );
        verify(this.employeeProfileRepository, only()).saveRelationship(this.employee);
    }
}
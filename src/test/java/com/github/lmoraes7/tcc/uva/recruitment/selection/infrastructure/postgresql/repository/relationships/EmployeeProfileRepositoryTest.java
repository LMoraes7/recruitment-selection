package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.batch.SaveEmployeeProfileBatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.query.EmployeeProfileCommands.SAVE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class EmployeeProfileRepositoryTest {

    private JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    private EmployeeProfileRepository employeeProfileRepository = new EmployeeProfileRepository(this.jdbcTemplate);

    private Employee employee;

    @BeforeEach
    void setUp() {
        this.employee = dummyObject(Employee.class);
    }

    @Test
    void when_prompted_should_successfully_save_the_relationship() {
        when(this.jdbcTemplate.batchUpdate(
                SAVE.sql,
                new SaveEmployeeProfileBatch(
                        this.employee.getIdentifier(),
                        this.employee.getAccessCredentials().getProfiles().stream().map(Profile::getIdentifier).toList()
                )
        )).thenReturn(new int[] {});

        assertDoesNotThrow(() -> this.employeeProfileRepository.saveRelationship(this.employee));

        verify(this.jdbcTemplate, only()).batchUpdate(
                SAVE.sql,
                new SaveEmployeeProfileBatch(
                        this.employee.getIdentifier(),
                        this.employee.getAccessCredentials().getProfiles().stream().map(Profile::getIdentifier).toList()
                )
        );
    }

}
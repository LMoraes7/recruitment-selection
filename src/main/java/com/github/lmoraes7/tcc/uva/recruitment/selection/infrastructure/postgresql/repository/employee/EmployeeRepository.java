package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.entity.EmployeeEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.EmployeeProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.converter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.query.EmployeeCommands.CHANGE_PASSWORD_BY_USERNAME;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.query.EmployeeCommands.SAVE;

@Repository
public class EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final EmployeeProfileRepository employeeProfileRepository;


    @Autowired
    public EmployeeRepository(
            final JdbcTemplate jdbcTemplate,
            final EmployeeProfileRepository employeeProfileRepository
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.employeeProfileRepository = employeeProfileRepository;
    }

    public Employee save(final Employee employee) {
        final EmployeeEntity employeeEntity = toEntity(employee);

        this.jdbcTemplate.update(
                SAVE.sql,
                employeeEntity.getIdentifier(),
                employeeEntity.getPersonalData().getName(),
                employeeEntity.getPersonalData().getCpf(),
                employeeEntity.getPersonalData().getEmail(),
                employeeEntity.getPersonalData().getDateOfBirth(),
                employeeEntity.getPersonalData().getPhone(),
                employeeEntity.getPersonalData().getAddress(),
                employeeEntity.getAccessCredentials().getUsername(),
                employeeEntity.getAccessCredentials().getPassword()
        );
        this.employeeProfileRepository.saveRelationship(employee);
        return employee;
    }

    public void changePassword(final String username, final String password) {
        int update = this.jdbcTemplate.update(
                CHANGE_PASSWORD_BY_USERNAME.sql,
                password,
                username
        );

        if (update == 0)
            throw new NotFoundException(username, Employee.class);
    }

}

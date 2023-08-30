package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.batch.SaveProfileFunctionBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.query.EmployeeProfileCommands.SAVE;

@Repository
public class EmployeeProfileRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeProfileRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveRelationship(final Employee employee) {
        this.jdbcTemplate.batchUpdate(
                SAVE.sql,
                new SaveProfileFunctionBatch(
                        employee.getIdentifier(),
                        employee.getAccessCredentials().getProfiles().stream().map(Profile::getIdentifier).toList()
                )
        );
    }

}

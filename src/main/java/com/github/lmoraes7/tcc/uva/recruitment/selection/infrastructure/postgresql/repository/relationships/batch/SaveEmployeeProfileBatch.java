package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.batch;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public final class SaveEmployeeProfileBatch implements BatchPreparedStatementSetter {
    private final String employeeIdentifier;
    private final List<String> profileIdentifiers;

    public SaveEmployeeProfileBatch(
            final String employeeIdentifier,
            final List<String> profileIdentifiers
    ) {
        this.employeeIdentifier = employeeIdentifier;
        this.profileIdentifiers = profileIdentifiers;
    }

    @Override
    public void setValues(PreparedStatement ps, int index) throws SQLException {
        ps.setString(1, employeeIdentifier);
        ps.setString(2, profileIdentifiers.get(index));
    }

    @Override
    public int getBatchSize() {
        return profileIdentifiers.size();
    }

}

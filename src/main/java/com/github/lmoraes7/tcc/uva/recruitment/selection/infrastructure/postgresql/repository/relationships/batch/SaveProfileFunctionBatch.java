package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.batch;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class SaveProfileFunctionBatch implements BatchPreparedStatementSetter {
    private final String profileIdentifier;
    private final List<String> functionsIdentifiers;

    public SaveProfileFunctionBatch(
            final String profileIdentifier,
            final List<String> functionsIdentifiers
    ) {
        this.profileIdentifier = profileIdentifier;
        this.functionsIdentifiers = functionsIdentifiers;
    }

    @Override
    public void setValues(PreparedStatement ps, int index) throws SQLException {
        ps.setString(1, profileIdentifier);
        ps.setString(2, functionsIdentifiers.get(index));
    }

    @Override
    public int getBatchSize() {
        return functionsIdentifiers.size();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SaveProfileFunctionBatch that = (SaveProfileFunctionBatch) object;
        return Objects.equals(profileIdentifier, that.profileIdentifier) && Objects.equals(functionsIdentifiers, that.functionsIdentifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileIdentifier, functionsIdentifiers);
    }

}

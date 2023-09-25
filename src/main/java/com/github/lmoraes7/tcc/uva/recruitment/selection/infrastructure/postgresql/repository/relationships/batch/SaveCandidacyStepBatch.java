package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.batch;

import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.entity.StepCandidacyEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public final class SaveCandidacyStepBatch implements BatchPreparedStatementSetter {
    private final String candidacyIdentifier;
    private final List<StepCandidacyEntity> stepsEntity;

    public SaveCandidacyStepBatch(
            final String candidacyIdentifier,
            final List<StepCandidacyEntity> stepsEntity
    ) {
        this.candidacyIdentifier = candidacyIdentifier;
        this.stepsEntity = stepsEntity;
    }

    @Override
    public void setValues(final PreparedStatement ps, final int index) throws SQLException {
        ps.setString(1, candidacyIdentifier);
        ps.setString(2, stepsEntity.get(index).getIdentifier());
        ps.setString(3, stepsEntity.get(index).getNextStepIdentifier());
        ps.setString(4, stepsEntity.get(index).getStatus());
        ps.setObject(5, stepsEntity.get(index).getLimitTime(), JDBCType.BIGINT);
        ps.setObject(6, stepsEntity.get(index).getReleaseDate(), JDBCType.DATE);
    }

    @Override
    public int getBatchSize() {
        return stepsEntity.size();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SaveCandidacyStepBatch that = (SaveCandidacyStepBatch) object;
        return Objects.equals(candidacyIdentifier, that.candidacyIdentifier) && Objects.equals(stepsEntity, that.stepsEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidacyIdentifier, stepsEntity);
    }

}

package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.batch;

import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.entity.StepSelectiveProcessEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public final class SaveSelectiveProcessStepBatch implements BatchPreparedStatementSetter {
    private final String selectiveProcessIdentifier;
    private final List<StepSelectiveProcessEntity> stepsEntity;

    public SaveSelectiveProcessStepBatch(
            final String selectiveProcessIdentifier,
            final List<StepSelectiveProcessEntity> stepsEntity
    ) {
        this.selectiveProcessIdentifier = selectiveProcessIdentifier;
        this.stepsEntity = stepsEntity;
    }

    @Override
    public void setValues(final PreparedStatement ps, final int index) throws SQLException {
        ps.setString(1, selectiveProcessIdentifier);
        ps.setString(2, stepsEntity.get(index).getIdentifier());
        ps.setString(3, stepsEntity.get(index).getNextStepIdentifier());
        ps.setObject(4, stepsEntity.get(index).getLimitTime(), JDBCType.BIGINT);
    }

    @Override
    public int getBatchSize() {
        return stepsEntity.size();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SaveSelectiveProcessStepBatch that = (SaveSelectiveProcessStepBatch) object;
        return Objects.equals(selectiveProcessIdentifier, that.selectiveProcessIdentifier) && Objects.equals(stepsEntity, that.stepsEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectiveProcessIdentifier, stepsEntity);
    }

}

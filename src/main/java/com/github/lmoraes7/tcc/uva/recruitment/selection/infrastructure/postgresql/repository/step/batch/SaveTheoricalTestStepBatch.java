package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.batch;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public final class SaveTheoricalTestStepBatch implements BatchPreparedStatementSetter {
    private final String stepIdentifier;
    private final List<String> questionIdentifies;

    public SaveTheoricalTestStepBatch(
            final String stepIdentifier,
            final List<String> questionIdentifies
    ) {
        this.stepIdentifier = stepIdentifier;
        this.questionIdentifies = questionIdentifies;
    }

    @Override
    public void setValues(final PreparedStatement ps, final int index) throws SQLException {
        ps.setString(1, this.stepIdentifier);
        ps.setString(2, this.questionIdentifies.get(index));
    }

    @Override
    public int getBatchSize() {
        return this.questionIdentifies.size();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SaveTheoricalTestStepBatch that = (SaveTheoricalTestStepBatch) object;
        return Objects.equals(stepIdentifier, that.stepIdentifier) && Objects.equals(questionIdentifies, that.questionIdentifies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stepIdentifier, questionIdentifies);
    }

}

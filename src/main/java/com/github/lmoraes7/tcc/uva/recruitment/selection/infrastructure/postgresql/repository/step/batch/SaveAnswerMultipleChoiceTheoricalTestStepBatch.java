package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.batch;

import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.vo.StepBatch;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public final class SaveAnswerMultipleChoiceTheoricalTestStepBatch implements BatchPreparedStatementSetter {
    private final List<StepBatch> steps;

    public SaveAnswerMultipleChoiceTheoricalTestStepBatch(final List<StepBatch> steps) {
        this.steps = steps;
    }

    @Override
    public void setValues(final PreparedStatement ps, final int i) throws SQLException {
        ps.setString(1, this.steps.get(i).getCandidacyIdentifier());
        ps.setString(2, this.steps.get(i).getStepIdentifier());
        ps.setString(3, this.steps.get(i).getQuestionIdentifier());
        ps.setString(4, this.steps.get(i).getAnswerIdentifier());
        ps.setString(5, this.steps.get(i).getTypeQuestion().name());
        ps.setString(6, this.steps.get(i).getAnswerDiscursive());
    }

    @Override
    public int getBatchSize() {
        return this.steps.size();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SaveAnswerMultipleChoiceTheoricalTestStepBatch that = (SaveAnswerMultipleChoiceTheoricalTestStepBatch) object;
        return Objects.equals(steps, that.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(steps);
    }

}

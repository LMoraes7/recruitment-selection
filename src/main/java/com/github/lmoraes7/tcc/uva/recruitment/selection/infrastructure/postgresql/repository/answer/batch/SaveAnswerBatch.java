package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer.batch;

import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer.entity.AnswerEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public final class SaveAnswerBatch implements BatchPreparedStatementSetter {
    private final List<AnswerEntity> answerEntities;

    public SaveAnswerBatch(final List<AnswerEntity> answerEntities) {
        this.answerEntities = answerEntities;
    }

    @Override
    public void setValues(final PreparedStatement ps, final int index) throws SQLException {
        ps.setString(1, this.answerEntities.get(index).getIdentifier());
        ps.setString(2, this.answerEntities.get(index).getDescription());
        ps.setBoolean(3, this.answerEntities.get(index).getCorrect());
        ps.setString(4, this.answerEntities.get(index).getQuestionIdentifier());
    }

    @Override
    public int getBatchSize() {
        return this.answerEntities.size();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SaveAnswerBatch that = (SaveAnswerBatch) object;
        return Objects.equals(answerEntities, that.answerEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerEntities);
    }

}

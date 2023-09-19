package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.batch;

import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity.vo.FileVoEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public final class SaveUploadFileStepBatch implements BatchPreparedStatementSetter {
    private final String stepIdentifier;
    private final List<FileVoEntity> files;

    public SaveUploadFileStepBatch(
            final String stepIdentifier,
            final List<FileVoEntity> files
    ) {
        this.stepIdentifier = stepIdentifier;
        this.files = files;
    }

    @Override
    public void setValues(final PreparedStatement ps, final int index) throws SQLException {
        ps.setString(1, this.stepIdentifier);
        ps.setString(2, this.files.get(index).getDescription());
        ps.setString(3, this.files.get(index).getType());
    }

    @Override
    public int getBatchSize() {
        return this.files.size();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SaveUploadFileStepBatch that = (SaveUploadFileStepBatch) object;
        return Objects.equals(stepIdentifier, that.stepIdentifier) && Objects.equals(files, that.files);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stepIdentifier, files);
    }

}

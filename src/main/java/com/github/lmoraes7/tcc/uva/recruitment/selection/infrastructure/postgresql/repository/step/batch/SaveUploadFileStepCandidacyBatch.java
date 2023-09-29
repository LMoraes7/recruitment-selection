package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.batch;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteFileDto;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public final class SaveUploadFileStepCandidacyBatch implements BatchPreparedStatementSetter {
    private final String candidacyIdentifier;
    private final String stepIdentifier;
    private final List<ExecuteFileDto> files;

    public SaveUploadFileStepCandidacyBatch(
            final String candidacyIdentifier,
            final String stepIdentifier,
            final List<ExecuteFileDto> files
    ) {
        this.candidacyIdentifier = candidacyIdentifier;
        this.stepIdentifier = stepIdentifier;
        this.files = files;
    }


    @Override
    public void setValues(final PreparedStatement ps, final int i) throws SQLException {
        ps.setString(1, this.candidacyIdentifier);
        ps.setString(2, this.stepIdentifier);
        ps.setBytes(3, this.files.get(i).getBytes());
        ps.setString(4, this.files.get(i).getType().name());
    }

    @Override
    public int getBatchSize() {
        return this.files.size();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SaveUploadFileStepCandidacyBatch that = (SaveUploadFileStepCandidacyBatch) object;
        return Objects.equals(candidacyIdentifier, that.candidacyIdentifier) && Objects.equals(stepIdentifier, that.stepIdentifier) && Objects.equals(files, that.files);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidacyIdentifier, stepIdentifier, files);
    }

}

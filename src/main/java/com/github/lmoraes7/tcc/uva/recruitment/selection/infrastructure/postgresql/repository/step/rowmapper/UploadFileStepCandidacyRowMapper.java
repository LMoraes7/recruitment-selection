package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeFile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo.TheoricalTestStepCandidacyVo;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo.UploadFileStepCandidacyVo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public final class UploadFileStepCandidacyRowMapper implements RowMapper<UploadFileStepCandidacyVo> {

    @Override
    public UploadFileStepCandidacyVo mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new UploadFileStepCandidacyVo(
                rs.getString("candidacy_id"),
                rs.getString("candidate_id"),
                rs.getString("selective_process_id"),
                rs.getString("step_id"),
                rs.getString("file_description"),
                TypeFile.valueOf(rs.getString("file_type"))
        );
    }

}

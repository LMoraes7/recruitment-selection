package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper;

import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo.ExternalStepCandidacyVo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public final class ExternalStepCandidacyRowMapper implements RowMapper<ExternalStepCandidacyVo> {

    @Override
    public ExternalStepCandidacyVo mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new ExternalStepCandidacyVo(
                rs.getString("candidacy_id"),
                rs.getString("candidate_id"),
                rs.getString("selective_process_id"),
                rs.getString("step_id"),
                rs.getString("external_link"),
                rs.getObject("external_date_and_time", LocalDateTime.class)
        );
    }

}

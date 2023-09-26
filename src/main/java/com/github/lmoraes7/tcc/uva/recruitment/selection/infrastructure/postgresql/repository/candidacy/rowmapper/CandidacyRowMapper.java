package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.rowmapper;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.rowmapper.vo.CandidacyVo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public final class CandidacyRowMapper implements RowMapper<CandidacyVo> {

    @Override
    public CandidacyVo mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new CandidacyVo(
                rs.getString("application_id"),
                StatusCandidacy.valueOf(rs.getString("application_status")),
                rs.getString("selective_process_id"),
                rs.getString("selective_process_title"),
                rs.getString("application_step_id"),
                rs.getString("application_step_id_next"),
                StatusStepCandidacy.valueOf(rs.getString("application_step_status")),
                rs.getString("step_title"),
                TypeStep.valueOf(rs.getString("step_type"))
        );
    }

}

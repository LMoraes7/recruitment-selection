package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo.StepFindVo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public final class StepFindRowMapper implements RowMapper<StepFindVo> {

    @Override
    public StepFindVo mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new StepFindVo(
                rs.getString("step_id"),
                StatusStepCandidacy.valueOf(rs.getString("step_status")),
                rs.getLong("step_limit_time"),
                rs.getObject("step_release_date", LocalDate.class)
        );
    }

}

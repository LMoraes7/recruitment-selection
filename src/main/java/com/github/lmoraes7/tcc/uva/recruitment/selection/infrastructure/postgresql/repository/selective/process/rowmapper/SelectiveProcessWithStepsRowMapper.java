package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.rowmapper;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.rowmapper.vo.SelectiveProcessStepsVo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public final class SelectiveProcessWithStepsRowMapper implements RowMapper<SelectiveProcessStepsVo> {

    @Override
    public SelectiveProcessStepsVo mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new SelectiveProcessStepsVo(
                rs.getString("id_selective_process"),
                StatusSelectiveProcess.valueOf(rs.getString("status_selective_process")),
                rs.getString("id_step"),
                rs.getString("id_next_step"),
                rs.getLong("step_limit_time"),
                TypeStep.valueOf(rs.getString("step_type"))
        );
    }

}

package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Step;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public final class StepWithIdAndTypeRowMapper implements RowMapper<Step> {

    @Override
    public Step mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new ExternalStep(new StepData(rs.getString("id"), TypeStep.valueOf(rs.getString("type"))));
    }

}

package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.rowmapper;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public final class SelectiveProcessPageRowMapper implements RowMapper<SelectiveProcess> {

    @Override
    public SelectiveProcess mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new SelectiveProcess(
                rs.getString("id"),
                rs.getString("title")
        );
    }

}

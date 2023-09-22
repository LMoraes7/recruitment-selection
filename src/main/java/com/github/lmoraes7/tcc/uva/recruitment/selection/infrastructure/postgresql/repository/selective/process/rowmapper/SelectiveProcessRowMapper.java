package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.rowmapper;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.utils.ArraySqlValue;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;

@Component
public final class SelectiveProcessRowMapper implements RowMapper<SelectiveProcess> {

    @Override
    public SelectiveProcess mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new SelectiveProcess(
                rs.getString("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("desired_position"),
                StatusSelectiveProcess.valueOf(rs.getString("status")),
                Set.of(resolveArray(rs.getArray("responsibilities")).getArray()),
                Set.of(resolveArray(rs.getArray("requirements")).getArray()),
                Set.of(resolveArray(rs.getArray("additional_infos")).getArray())
        );
    }

    private ArraySqlValue<String> resolveArray(final Array array) {
        if (array == null)
            return null;

        try {
            return ArraySqlValue.create(
                    Arrays.stream((Object[]) array.getArray())
                            .map(Object::toString)
                            .toList()
                            .toArray(new String[0])
            );
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

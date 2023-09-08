package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.rowmapper;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.PasswordChangeRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public final class PasswordChangeRequestRowMapper implements RowMapper<PasswordChangeRequest> {

    @Override
    public PasswordChangeRequest mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new PasswordChangeRequest(
                rs.getString("code"),
                rs.getString("email_entity"),
                TypeEntity.valueOf(rs.getString("type_entity")),
                rs.getObject("requested_in", LocalDateTime.class),
                rs.getObject("expired_in", LocalDateTime.class),
                rs.getBoolean("is_used")
        );
    }

}

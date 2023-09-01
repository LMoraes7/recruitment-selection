package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.rowmapper;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public final class ProfileRowMapper implements RowMapper<Profile> {

    @Override
    public Profile mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new Profile(rs.getString("id"), rs.getString("name"));
    }

}

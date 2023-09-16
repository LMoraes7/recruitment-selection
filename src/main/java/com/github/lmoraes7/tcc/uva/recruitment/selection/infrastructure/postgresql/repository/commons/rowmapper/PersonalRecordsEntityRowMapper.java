package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.rowmapper;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.PersonalRecordsEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public final class PersonalRecordsEntityRowMapper implements RowMapper<PersonalRecordsEntity> {

    @Override
    public PersonalRecordsEntity mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new PersonalRecordsEntity(
                rs.getString("recorded_email"),
                rs.getString("recorded_document"),
                TypeEntity.valueOf(rs.getString("type_entity"))
        );
    }

}

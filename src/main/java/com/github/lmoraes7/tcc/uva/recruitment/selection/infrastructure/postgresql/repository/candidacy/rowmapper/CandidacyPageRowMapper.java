package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.rowmapper;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.rowmapper.vo.CandidacyPageVo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public final class CandidacyPageRowMapper implements RowMapper<CandidacyPageVo> {

    @Override
    public CandidacyPageVo mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new CandidacyPageVo(
                rs.getString("id"),
                StatusCandidacy.valueOf(rs.getString("status")),
                rs.getString("title")
        );
    }

}

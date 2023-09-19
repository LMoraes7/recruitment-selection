package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.rowmapper;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public final class QuestionWithTitleAndTypeRowMapper implements RowMapper<Question> {

    @Override
    public Question mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new Question(rs.getString("id"), TypeQuestion.valueOf(rs.getString("type")));
    }

}

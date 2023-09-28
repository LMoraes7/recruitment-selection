package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo.TheoricalTestStepCandidacyVo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public final class TheoricalTestStepCandidacyRowMapper implements RowMapper<TheoricalTestStepCandidacyVo> {

    @Override
    public TheoricalTestStepCandidacyVo mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new TheoricalTestStepCandidacyVo(
                rs.getString("candidacy_id"),
                rs.getString("candidate_id"),
                rs.getString("selective_process_id"),
                rs.getString("step_id"),
                rs.getString("question_id"),
                rs.getString("question_description"),
                TypeQuestion.valueOf(rs.getString("question_type")),
                rs.getString("answer_id"),
                rs.getString("answer_description")
        );
    }

}

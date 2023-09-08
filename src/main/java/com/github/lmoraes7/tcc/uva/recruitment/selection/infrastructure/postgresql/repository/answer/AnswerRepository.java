package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Answer;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer.batch.SaveAnswerBatch;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer.entity.AnswerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer.converter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer.query.AnswerCommands.SAVE;

@Repository
public class AnswerRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AnswerRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Answer> save(final String questionIdentifier, final Collection<Answer> answers) {
        final List<AnswerEntity> answerEntities = toEntity(questionIdentifier, answers);

        this.jdbcTemplate.batchUpdate(SAVE.sql, new SaveAnswerBatch(answerEntities));

        return answers;
    }

}

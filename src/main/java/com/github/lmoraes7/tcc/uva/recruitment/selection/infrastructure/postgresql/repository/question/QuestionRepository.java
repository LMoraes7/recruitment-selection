package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer.AnswerRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.entity.QuestionEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.rowmapper.QuestionWithIdAndTypeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.converter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.query.QuestionCommands.SAVE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.query.QuestionCommands.SELECT_IDENTIFIERS_IN;

@Repository
public class QuestionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final AnswerRepository answerRepository;
    private final QuestionWithIdAndTypeRowMapper questionWithIdAndTypeRowMapper;

    @Autowired
    public QuestionRepository(
            final JdbcTemplate jdbcTemplate,
            final AnswerRepository answerRepository,
            final QuestionWithIdAndTypeRowMapper questionWithIdAndTypeRowMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.answerRepository = answerRepository;
        this.questionWithIdAndTypeRowMapper = questionWithIdAndTypeRowMapper;
    }

    public Collection<Question> fetchQuestion(final Collection<String> identifiers) {
        final String inSql = String.join(",", Collections.nCopies(identifiers.size(), "?"));
        return this.jdbcTemplate.query(
                String.format(SELECT_IDENTIFIERS_IN.sql, inSql),
                this.questionWithIdAndTypeRowMapper,
                identifiers.toArray()
        );
    }

    public Question saveWithoutAnswers(final Question question) {
        final QuestionEntity questionEntity = toEntity(question);

        this.jdbcTemplate.update(
                SAVE.sql,
                questionEntity.getIdentifier(),
                questionEntity.getDescription(),
                questionEntity.getType()
        );

        return question;
    }

    public Question saveWithAnswers(final Question question) {
        final QuestionEntity questionEntity = toEntity(question);

        this.jdbcTemplate.update(
                SAVE.sql,
                questionEntity.getIdentifier(),
                questionEntity.getDescription(),
                questionEntity.getType()
        );

        this.answerRepository.save(question.getIdentifier(), question.getAnswers());
        return question;
    }

}

package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer.AnswerRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.entity.QuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.converter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.query.QuestionCommands.SAVE;

@Repository
public class QuestionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final AnswerRepository answerRepository;

    @Autowired
    public QuestionRepository(final JdbcTemplate jdbcTemplate, final AnswerRepository answerRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.answerRepository = answerRepository;
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

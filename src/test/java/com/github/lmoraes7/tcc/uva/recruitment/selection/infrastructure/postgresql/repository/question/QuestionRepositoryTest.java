package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer.AnswerRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.entity.QuestionEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.rowmapper.QuestionWithTitleAndTypeRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.converter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.query.QuestionCommands.SAVE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.query.QuestionCommands.SELECT_IDENTIFIERS_IN;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class QuestionRepositoryTest {

    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final AnswerRepository answerRepository = mock(AnswerRepository.class);
    private final QuestionWithTitleAndTypeRowMapper questionWithTitleAndTypeRowMapper = mock(QuestionWithTitleAndTypeRowMapper.class);
    private final QuestionRepository questionRepository = new QuestionRepository(
            this.jdbcTemplate,
            this.answerRepository,
            this.questionWithTitleAndTypeRowMapper
    );

    private Question question;
    private List<String> identifiers;

    @BeforeEach
    void setUp() {
        this.question = dummyObject(Question.class);
        this.identifiers = List.of(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
    }

    @Test
    void when_prompted_should_successfully_save_a_question_with_answers() {
        final QuestionEntity questionEntity = toEntity(question);

        when(this.jdbcTemplate.update(
                SAVE.sql,
                questionEntity.getIdentifier(),
                questionEntity.getDescription(),
                questionEntity.getType()
        )).thenReturn(1);

        assertDoesNotThrow(() -> this.questionRepository.saveWithAnswers(this.question));

        verify(this.jdbcTemplate, only()).update(
                SAVE.sql,
                questionEntity.getIdentifier(),
                questionEntity.getDescription(),
                questionEntity.getType()
        );
        verify(this.answerRepository, only()).save(this.question.getIdentifier(), this.question.getAnswers());
    }

    @Test
    void when_prompted_should_successfully_save_a_question_without_answers() {
        final QuestionEntity questionEntity = toEntity(question);

        when(this.jdbcTemplate.update(
                SAVE.sql,
                questionEntity.getIdentifier(),
                questionEntity.getDescription(),
                questionEntity.getType()
        )).thenReturn(1);

        assertDoesNotThrow(() -> this.questionRepository.saveWithoutAnswers(this.question));

        verify(this.jdbcTemplate, only()).update(
                SAVE.sql,
                questionEntity.getIdentifier(),
                questionEntity.getDescription(),
                questionEntity.getType()
        );
        verifyNoInteractions(this.answerRepository);
    }

    @Test
    void when_requested_you_must_successfully_search_for_questions() {
        final String inSql = String.join(",", Collections.nCopies(this.identifiers.size(), "?"));

        when(this.jdbcTemplate.query(
                String.format(SELECT_IDENTIFIERS_IN.sql, inSql),
                this.questionWithTitleAndTypeRowMapper,
                this.identifiers.toArray()
        )).thenReturn(List.of(this.question));

        assertDoesNotThrow(() -> this.questionRepository.fetchQuestion(this.identifiers));

        verify(this.jdbcTemplate, only()).query(
                String.format(SELECT_IDENTIFIERS_IN.sql, inSql),
                this.questionWithTitleAndTypeRowMapper,
                this.identifiers.toArray()
        );
        verifyNoInteractions(this.answerRepository);
    }

}
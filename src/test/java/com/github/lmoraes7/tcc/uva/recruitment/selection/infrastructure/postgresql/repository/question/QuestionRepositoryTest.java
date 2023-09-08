package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer.AnswerRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.entity.QuestionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.converter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.question.query.QuestionCommands.SAVE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class QuestionRepositoryTest {

    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final AnswerRepository answerRepository = mock(AnswerRepository.class);
    private final QuestionRepository questionRepository = new QuestionRepository(
            this.jdbcTemplate,
            this.answerRepository
    );

    private Question question;

    @BeforeEach
    void setUp() {
        this.question = dummyObject(Question.class);
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

}
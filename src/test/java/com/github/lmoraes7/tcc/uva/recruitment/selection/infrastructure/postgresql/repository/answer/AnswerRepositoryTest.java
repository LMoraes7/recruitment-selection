package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Answer;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer.batch.SaveAnswerBatch;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer.entity.AnswerEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.UUID;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer.converter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.answer.query.AnswerCommands.SAVE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class AnswerRepositoryTest {

    private final JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    private final AnswerRepository answerRepository = new AnswerRepository(this.jdbcTemplate);

    private List<Answer> answers;
    private String questionIdentifier;

    @BeforeEach
    void setUp() {
        this.answers = List.of(TestUtils.dummyObject(Answer.class));
        this.questionIdentifier = UUID.randomUUID().toString();
    }

    @Test
    void when_prompted_should_save_a_response_successfully() {
        final List<AnswerEntity> answerEntities = toEntity(questionIdentifier, answers);

        when(this.jdbcTemplate.batchUpdate(SAVE.sql, new SaveAnswerBatch(answerEntities))).thenReturn(new int[] {});

        assertDoesNotThrow(() -> this.answerRepository.save(this.questionIdentifier, this.answers));

        verify(this.jdbcTemplate, only()).batchUpdate(SAVE.sql, new SaveAnswerBatch(answerEntities));
    }

}